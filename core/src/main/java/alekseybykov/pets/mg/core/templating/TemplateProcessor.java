package alekseybykov.pets.mg.core.templating;

import alekseybykov.pets.mg.core.coreconsts.EncodingScheme;
import alekseybykov.pets.mg.core.businessobjects.file.OAGISFile;
import alekseybykov.pets.mg.core.businessobjects.file.TFFFile;
import alekseybykov.pets.mg.core.io.UUIDFilterInputStream;
import alekseybykov.pets.mg.core.logging.UILogger;
import alekseybykov.pets.mg.core.utils.io.EncodeUtils;
import alekseybykov.pets.mg.core.utils.io.IOStreamUtils;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.*;

/**
 * Класс выполняет обработку загружаемых документов/вложений.
 * В теле документа/вложения могут быть представлены два типа шаблонов -
 * "$uuid" и/или "$doc_uuid". В задачи данного класса входит найти данные шаблоны и
 * заменить на UUID:
 *
 * 1) Для документа $uuid заменяется на сгенерированный случайный UUID (например, для тега BODId).
 * $doc_uuid заменяется случайным UUID, который будет подставлен в тег xml, где данный шаблон был найден (например, DocGUID).
 * и в поле DOC_GUID таблицы TB_MESSAGE_BIG_ATTRIBUTES.
 *
 * 2) Для вложения $uuid заменяется на сгенерированный случайный UUID (например, для тега BODId).
 * $doc_uuid заменяется случайным UUID, который будет подставлен и в тег xml где данный шаблон был найден,
 * и в поле ATT_GUID таблицы TB_MESSAGE_BIG_ATTRIBUTES.
 *
 * @author bykov.alexey
 * @since 08.03.2021
 */
@Slf4j
public class TemplateProcessor {

	private static final UILogger uiLogger = UILogger.getInstance();

	public static File process(TFFFile tffFile, String uuid) {
		File processedFile = null;
		try (FileInputStream tffInputStream = new FileInputStream(tffFile.getPath())) {
			File encodedFile = processSingleTFFBytes(tffInputStream, uuid);
			processedFile = EncodeUtils.decodeFile(encodedFile, EncodingScheme.BASE64);
		} catch (IOException e) {
			val fileName = tffFile.getName();
			log.error("Не удалось обработать файл {}. Исключение: ", fileName, e);
			uiLogger.log("<b><font color=\"#FF1832\">Не удалось обработать файл " + fileName + "</font></b><br/>");
		}
		return processedFile;
	}

	/**
	 * Метод выполняет обработку документа.
	 *
	 * @param oagisFile - класс, инкапсулирующий xml-документ (DTO, переданный из слоя GUI).
	 * @param docUUID - UUID, на который заменяется шаблон $doc_uuid.
	 * @return - Ссылка на файл, в котором заменены шаблоны $uuid/$doc_uuid.
	 *           Файл закодирован в Base64, чтобы не потерялась информация о кодировке, которая неизвестна.
	 */
	public static File process(OAGISFile oagisFile, String docUUID) {
		File processedFile = null;
		File xmlFile = new File(oagisFile.getPath()).getAbsoluteFile();

		try (FileInputStream xmlInputStream = new FileInputStream(xmlFile)) {
			processedFile = processRawBytes(xmlInputStream, docUUID, null);

		} catch (IOException e) {
			val fileName = oagisFile.getName();
			log.error("Не удалось обработать файл {}. Исключение: ", fileName, e);
			uiLogger.log("<b><font color=\"#FF1832\">Не удалось обработать файл " + fileName + "</font></b><br/>");
		}

		return processedFile;
	}

	/**
	 * Метод выполняет обработку вложения.
	 *
	 * @param attachmentInputStream - поток XML-вложения для чтения.
	 * @param attachUUID - UUID, на который заменяется шаблон $uuid.
	 * @param docUUID - UUID, на который заменяется шаблон $doc_uuid.
	 * @return -
	 */
	public static File process(InputStream attachmentInputStream, String attachUUID, String docUUID) {
		File file = null;
		try {
			file = processRawBytes(attachmentInputStream, docUUID, attachUUID);

		} catch (IOException e) {
			log.error("Не удалось обработать вложение {}. Исключение: ", attachUUID, e);
			uiLogger.log("<b><font color=\"#FF1832\">Не удалось обработать вложение " + attachUUID + "</font></b><br/>");
		}

		return file;
	}

	public static File processTicket(InputStream inputStream, String uuid, String sender, String recipient) {
		File processedFile = null;
		try {
			File encodedFile = process(inputStream, uuid, sender, recipient);
			processedFile = EncodeUtils.decodeFile(encodedFile, EncodingScheme.BASE64);
		} catch (IOException e) {
			// todo
		}
		return processedFile;
	}

	private static File process(InputStream inputStream, String uuid, String sender, String recipient) throws IOException {
		try (BufferedInputStream bis = new BufferedInputStream(inputStream);
		     ByteArrayInputStream bais = IOStreamUtils.readToByteArrayInputStream(bis);

		     UUIDFilterInputStream requestSenderIS = new UUIDFilterInputStream(bais, TemplatePlaceholders.REQUEST_SENDER_PLACEHOLDER.getValue(), sender);
		     UUIDFilterInputStream requestRecipientIS = new UUIDFilterInputStream(requestSenderIS, TemplatePlaceholders.REQUEST_RECIPIENT_PLACEHOLDER.getValue(), recipient);

		     UUIDFilterInputStream responseRecipientIS = new UUIDFilterInputStream(requestRecipientIS, TemplatePlaceholders.RESPONSE_RECIPIENT_PLACEHOLDER.getValue(), sender);
		     UUIDFilterInputStream responseSenderIS = new UUIDFilterInputStream(responseRecipientIS, TemplatePlaceholders.RESPONSE_SENDER_PLACEHOLDER.getValue(), recipient);

		     UUIDFilterInputStream resultIS = new UUIDFilterInputStream(responseSenderIS, TemplatePlaceholders.UUID_PLACEHOLDER.getValue(), uuid)
		) {
			return EncodeUtils.encodeToTempFile(resultIS);
		}
	}

	private static File processRawBytes(InputStream inputStream, String docUUID, String attachUUID) throws IOException {
		try (BufferedInputStream bis = new BufferedInputStream(inputStream);
		     ByteArrayInputStream bais = IOStreamUtils.readToByteArrayInputStream(bis);

		     // Цепочка потоков. Сначала в UUIDFilterInputStream обрабатывается исходный поток вложения, выполняется поиск и замена шаблона $uuid.
		     // Затем полученный обработанный поток снова обрабатывается в UUIDFilterInputStream, выполняется поиск и замена шаблона $doc_uuid.
		     // В UUIDFilterInputStream переопределен метод read(), поэтому обработка начинается с вызова данного метода (см. IOStreamUtils#readToByteArray)
		     UUIDFilterInputStream uuidFilteredIS = new UUIDFilterInputStream(bais, TemplatePlaceholders.UUID_PLACEHOLDER.getValue(), attachUUID);
		     UUIDFilterInputStream docUuidFilteredIS = new UUIDFilterInputStream(uuidFilteredIS, TemplatePlaceholders.DOC_UUID_PLACEHOLDER.getValue(), docUUID)
		) {
			// Считываем файл, побайтно ищем и заменяем шаблоны $uuid/$doc_uuid. В процессе чтения и замены
			// записываем небольшие буфферы из памяти (THREE_KB) во временный файл в кодировке Base64,
			// чтобы избежать OutOfMemory и не потерять информацию об исходной кодировке байт (которая должна оставаться неизвестной).
			return EncodeUtils.encodeToTempFile(docUuidFilteredIS);
		}
	}

	private static File processSingleTFFBytes(InputStream inputStream, String uuid) throws IOException {
		try (BufferedInputStream bis = new BufferedInputStream(inputStream);
		     ByteArrayInputStream bais = IOStreamUtils.readToByteArrayInputStream(bis);
		     UUIDFilterInputStream uuidFilteredIS = new UUIDFilterInputStream(bais, TemplatePlaceholders.UUID_PLACEHOLDER.getValue(), uuid);
		) {
			// Считываем файл, побайтно ищем и заменяем шаблоны $uuid/$doc_uuid. В процессе чтения и замены
			// записываем небольшие буфферы из памяти (THREE_KB) во временный файл в кодировке Base64,
			// чтобы избежать OutOfMemory и не потерять информацию об исходной кодировке байт (которая должна оставаться неизвестной).
			return EncodeUtils.encodeToTempFile(uuidFilteredIS);
		}
	}
}
