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

@Slf4j
public class TemplateProcessor {

	private static final UILogger uiLogger = UILogger.getInstance();

	public static File process(
			TFFFile tffFile,
			String uuid
	) {
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

	public static File process(
			OAGISFile oagisFile,
			String docUUID
	) {
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

	public static File process(
			InputStream attachmentInputStream,
			String attachUUID,
			String docUUID
	) {
		File file = null;
		try {
			file = processRawBytes(attachmentInputStream, docUUID, attachUUID);

		} catch (IOException e) {
			log.error("Не удалось обработать вложение {}. Исключение: ", attachUUID, e);
			uiLogger.log("<b><font color=\"#FF1832\">Не удалось обработать вложение " + attachUUID + "</font></b><br/>");
		}

		return file;
	}

	public static File processTicket(
			InputStream inputStream,
			String uuid,
			String sender,
			String recipient
	) {
		File processedFile = null;
		try {
			File encodedFile = process(inputStream, uuid, sender, recipient);
			processedFile = EncodeUtils.decodeFile(encodedFile, EncodingScheme.BASE64);
		} catch (IOException e) {
			// todo
		}
		return processedFile;
	}

	private static File process(
			InputStream inputStream,
			String uuid,
			String sender,
			String recipient
	) throws IOException {
		try (BufferedInputStream bis = new BufferedInputStream(inputStream);
		     ByteArrayInputStream bais = IOStreamUtils.readToByteArrayInputStream(bis);

		     UUIDFilterInputStream requestSenderIS = new UUIDFilterInputStream(
					 bais,
					 TemplatePlaceholders.REQUEST_SENDER_PLACEHOLDER.getValue(),
					 sender
			 );
		     UUIDFilterInputStream requestRecipientIS = new UUIDFilterInputStream(
					 requestSenderIS,
					 TemplatePlaceholders.REQUEST_RECIPIENT_PLACEHOLDER.getValue(),
					 recipient
			 );

		     UUIDFilterInputStream responseRecipientIS = new UUIDFilterInputStream(
					 requestRecipientIS,
					 TemplatePlaceholders.RESPONSE_RECIPIENT_PLACEHOLDER.getValue(),
					 sender
			 );
		     UUIDFilterInputStream responseSenderIS = new UUIDFilterInputStream(
					 responseRecipientIS,
					 TemplatePlaceholders.RESPONSE_SENDER_PLACEHOLDER.getValue(),
					 recipient
			 );

		     UUIDFilterInputStream resultIS = new UUIDFilterInputStream(
					 responseSenderIS,
					 TemplatePlaceholders.UUID_PLACEHOLDER.getValue(),
					 uuid
			 )
		) {
			return EncodeUtils.encodeToTempFile(resultIS);
		}
	}

	private static File processRawBytes(
			InputStream inputStream,
			String docUUID,
			String attachUUID
	) throws IOException {
		try (BufferedInputStream bis = new BufferedInputStream(inputStream);
		     ByteArrayInputStream bais = IOStreamUtils.readToByteArrayInputStream(bis);

		     UUIDFilterInputStream uuidFilteredIS = new UUIDFilterInputStream(
					 bais,
					 TemplatePlaceholders.UUID_PLACEHOLDER.getValue(),
					 attachUUID
			 );
		     UUIDFilterInputStream docUuidFilteredIS = new UUIDFilterInputStream(
					 uuidFilteredIS,
					 TemplatePlaceholders.DOC_UUID_PLACEHOLDER.getValue(),
					 docUUID
			 )
		) {
			return EncodeUtils.encodeToTempFile(docUuidFilteredIS);
		}
	}

	private static File processSingleTFFBytes(
			InputStream inputStream,
			String uuid
	) throws IOException {
		try (BufferedInputStream bis = new BufferedInputStream(inputStream);
		     ByteArrayInputStream bais = IOStreamUtils.readToByteArrayInputStream(bis);
		     UUIDFilterInputStream uuidFilteredIS = new UUIDFilterInputStream(
					 bais,
					 TemplatePlaceholders.UUID_PLACEHOLDER.getValue(),
					 uuid
			 );
		) {
			return EncodeUtils.encodeToTempFile(uuidFilteredIS);
		}
	}
}
