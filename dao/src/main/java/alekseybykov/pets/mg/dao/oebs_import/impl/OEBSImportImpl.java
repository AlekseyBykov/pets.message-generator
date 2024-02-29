package alekseybykov.pets.mg.dao.oebs_import.impl;

import alekseybykov.pets.mg.core.businessobjects.file.AttachmentFile;
import alekseybykov.pets.mg.core.businessobjects.file.OAGISFile;
import alekseybykov.pets.mg.core.coreconsts.EncodingScheme;
import alekseybykov.pets.mg.core.db.oracle.OracleConnectionManager;
import alekseybykov.pets.mg.core.logging.UILogger;
import alekseybykov.pets.mg.core.templating.TemplateProcessor;
import alekseybykov.pets.mg.core.utils.archive.GzipUtils;
import alekseybykov.pets.mg.core.utils.archive.ZipUtils;
import alekseybykov.pets.mg.core.utils.io.AttachmentProcessor;
import alekseybykov.pets.mg.core.utils.io.EncodeUtils;
import alekseybykov.pets.mg.dao.oebs_import.OEBSImportDao;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import lombok.var;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author bykov.alexey
 * @since 01.05.2016
 *
 * todo препроцессинг xml нужно вынести в отдельный сервис, это не функционал DAO.
 */
@Slf4j
@Repository
public class OEBSImportImpl implements OEBSImportDao {

	private static final UILogger uiLogger = UILogger.getInstance();

	/**
	 * Метод сохраняет в БД выбранный в поле "XML-файл" документ и вложения, добавленные
	 * в таблицу UI "Список загружаемых вложений".
	 * <p>
	 * Файлы документа и вложений обрабатываются побайтно и в процессе обработки сохраняются небольшими буферами во временные каталоги.
	 * Обработка необходима для замены шаблонов $uuid и $doc_uuid на гуиды, при этом сохранение во временные
	 * файлы и оперирование потоками (InputStream) избавляет от необходимости хранить большие буферы байт в ОЗУ.
	 * Потоки занимают небольшие буферы в ОЗУ, т.к. основная задача потока - это последовательная передача данных.
	 * <p>
	 * Вложения представлены списком, в котором могут быть как XML-файлы, так и архивы.
	 * В архиве может находиться несколько XML-файлов, все они обрабатываются по отдельности - также
	 * с минимальной загруженностью ОЗУ. После обработки все файлы упаковываются в новый временный архив,
	 * который далее передается в БД.
	 * <p>
	 * Поддерживаемые форматы архивов: zip, gzip. При этом, согласно спецификации zip, в gzip-архив может быть упакован только один файл.
	 *
	 * @param oagisFile - OAGIS файл.
	 * @param attachmentFiles - список файлов вложений.
	 */
	@Override
	@SneakyThrows
	public void importOagisFileWithAttachmentFiles(@NotNull OAGISFile oagisFile,
	                                        @NotNull List<AttachmentFile> attachmentFiles) {
		// Идентификатор записи в таблице tb_message, т.е. айди документа.
		// Для всех вложений к данному конкретному документу этот идентификатор один и тот же (т.к. документ один и тот же).
		var tbMessageId = getNexTbMessageId();

		// docGUID документа, по которому осуществляется связь с вложениями.
		// Данный гуид будет прописан в docGUID документа, docGUID всех вложений и в поле
		// doc_guid таблицы tb_message_big_attributes.
		val docUUID = UUID.randomUUID().toString().toUpperCase();

		// Сначала сохраняются вложения к документу, чтобы документ не начал обрабатываться раньше времени
		storeAttachments(tbMessageId, docUUID, attachmentFiles);

		// Сохраняется сам XML документ, к которому прилагаются вложения.
		// Размер исходного документа отличается от размера обработанного документа,
		// поскольку шаблоны $uuid и $doc_uuid заменяются на гуиды (которые длиннее).
		storeXmlDocument(oagisFile, tbMessageId, docUUID);
	}

	/**
	 * Метод сохраняет в БД выбранный в поле "XML-файл" документ.
	 * <p>
	 * Файл документа обрабатывается побайтно и в процессе обработки сохраняются небольшими буферами во временный каталог.
	 * Обработка необходима для замены шаблонов $uuid и $doc_uuid на гуиды, при этом сохранение во временные
	 * файлы и оперирование потоками (InputStream) избавляет от необходимости хранить большие буферы байт в ОЗУ.
	 *
	 * @param oagisFile - класс, инкапсулирующий xml-документ (DTO, переданный из слоя GUI).
	 * @param tbMessageId - идентификатор записи в таблице tb_message, т.е. айди документа.
	 * @param docUUID     - docGUID документа, по которому осуществляется связь с вложениями.
	 *                    Данный гуид будет прописан в docGUID документа, docGUID всех вложений и в поле
	 *                    doc_guid таблицы tb_message_big_attributes.
	 */
	@SneakyThrows
	private void storeXmlDocument(OAGISFile oagisFile, long tbMessageId, String docUUID) {
		val msgFilePath = oagisFile.getPath();

		uiLogger.log("<font color=\"#120A8F\"><b>[генерация документа]</b></font><br/>");
		uiLogger.log("Будет сгенерирован документ с идентификатором = [<b>" + tbMessageId + "</b>]<br/>");
		uiLogger.log("Файл документа = [<b>" + msgFilePath + "</b>]<br/>");

		// Ссылка на файл, в котором заменены шаблоны $uuid/$doc_uuid. Файл закодирован в Base64, чтобы не потерялась
		// информация о кодировке, которая неизвестна.
		File processedFile = TemplateProcessor.process(oagisFile, docUUID);

		// Выполняем декодирование файла из Base64 без предположений об исходной кодировке,
		// тем самым сохраняем ее (неизвестной).
		File base64DecodedFile = EncodeUtils.decodeFile(processedFile, EncodingScheme.BASE64);

		saveDocumentAsBlob(tbMessageId, base64DecodedFile);
	}

	/**
	 * Метод сохраняет в вложения, добавленные в таблицу UI "Список загружаемых вложений".
	 * <p>
	 * Файлы вложений обрабатывается побайтно и в процессе обработки сохраняются небольшими буферами во временные каталоги.
	 * Обработка необходима для замены шаблонов $uuid и $doc_uuid на гуиды, при этом сохранение во временные
	 * файлы и оперирование потоками (InputStream) избавляет от необходимости хранить большие буферы байт в ОЗУ.
	 * Потоки занимают небольшие буферы в ОЗУ, т.к. основная задача потока - это последовательная передача данных.
	 * <p>
	 * Вложения представлены списком, в котором могут быть как XML-файлы, так и архивы.
	 * В архиве может находиться несколько XML-файлов, все они обрабатываются по отдельности - также
	 * с минимальной загруженностью ОЗУ. После обработки все файлы упаковываются в новый временный архив,
	 * который далее передается в БД.
	 * <p>
	 * Поддерживаемые форматы архивов: zip, gzip.
	 *
	 * @param tbMessageId - идентификатор записи в таблице tb_message, т.е. айди документа.
	 * @param docUUID     - docGUID документа, по которому осуществляется связь с вложениями.
	 *                    Данный гуид будет прописан в docGUID документа, docGUID всех вложений и в поле doc_guid таблицы tb_message_big_attributes.
	 * @param attachments - список вложений, выбранных в UI "Список загружаемых вложений".
	 */
	@SneakyThrows
	private void storeAttachments(long tbMessageId, String docUUID, List<AttachmentFile> attachments) {
		if (attachments.isEmpty()) {
			uiLogger.log("<font color=\"#120A8F\"><b>[генерация вложений к документу не производится (список вложений пустой)]</b></font><br/><br/>");
			return;
		}

		uiLogger.log("<font color=\"#120A8F\"><b>[генерация вложений к документу]</b></font>");

		// Цикл по всем вложениям к документу, выбранным в UI "Список загружаемых вложений".
		for (var attachmentCounter = NumberUtils.INTEGER_ZERO; attachmentCounter < attachments.size(); attachmentCounter++) {
			AttachmentFile attachment = attachments.get(attachmentCounter);
			File attachmentFile = new File(attachment.getPath());

			// Идентификатор записи в таблице tb_message_big_attributes, т.е. айди вложения.
			// Каждое вложение представлено записью в таблице tb_message_big_attributes и имеет уникальный идентификатор.
			// При этом tb_msg_id может быть один и тот же, если вложений несколько.
			var tbMessageBigAttrId = getNextTbMessageBigAttrId();

			uiLogger.log("Генерируется вложение с ID = [<b>" + tbMessageBigAttrId + "</b>] к документу с ID = [<b>" + tbMessageId + "</b>]<br/>");

			val attachName = attachment.getName();
			val attachPath = attachment.getPath();

			uiLogger.log("Файл вложения = [<b>" + attachPath + "</b>]<br/>");
			uiLogger.log("Имя вложения = [<b>" + attachName + "</b>] " + makeAttachDetailsLogString(attachPath, attachName));

			if (ZipUtils.isZipArchive(attachmentFile)) {
				storeZipAttachment(attachment, attachmentCounter, docUUID, tbMessageId, tbMessageBigAttrId);
			} else if (GzipUtils.isGzipArchive(attachmentFile)) {
				storeGzipAttachment(attachment, attachmentCounter, docUUID, tbMessageId, tbMessageBigAttrId);
			} else {
				storeXmlAttachment(attachment, attachmentCounter, docUUID, tbMessageId, tbMessageBigAttrId);
			}

			// todo добавить поддержку tar.gz/tgz (несколько gz архивов в одном tgz).
		}

		uiLogger.log("<font color=\"#120A8F\"><b>[генерация вложений к документу завершена]</b></font><br/><br/>");
	}

	private String makeAttachDetailsLogString(String attachPath, String attachName) {
		return StringUtils.equalsIgnoreCase(FilenameUtils.getName(attachPath), attachName)
		       ? "<br/>"
		       : "(вложение передается c новым именем)<br/>";
	}

	/**
	 * Метод сохраняет вложение, представленное zip-архивом. При этом в blob поле сохраняется архив,
	 * полученный путем преобразования исходного выбранного архива на диске.
	 * Исходный архив распаковывается во временный каталог, во всех его файлах заменяются шаблоны $doc_uuid/$uuid,
	 * файлы сохраняются во временном каталоге в Base64. Далее поизводится упаковка полученных таким путем временных файлов,
	 * предварительно раскодированных из Base64, в новый временный zip-архив, который и передается как поток для чтения в JDBC insert.
	 *
	 * @param attachment         - объект, инкапсулирующий вложение выбранное в UI "Список загружаемых вложений" (DTO, переданный из GUI слоя).
	 * @param attachmentCounter  - номер вложения (ordinal_number в таблице tb_message_big_attributes).
	 * @param docUUID            - docGUID документа, по которому осуществляется связь с вложениями.
	 * @param tbMessageId        - идентификатор записи в таблице tb_message, т.е. айди документа.
	 * @param tbMessageBigAttrId - идентификатор записи в таблице tb_message_big_attributes, т.е. айди вложения.
	 */
	@SneakyThrows
	private void storeZipAttachment(AttachmentFile attachment,
	                                int attachmentCounter,
	                                String docUUID,
	                                long tbMessageId,
	                                long tbMessageBigAttrId) {

		val attachUUID = UUID.randomUUID().toString().toUpperCase();
		File processedZip = AttachmentProcessor.processZipAttachment(attachment, attachUUID, docUUID);

		saveAttachmentAsBlob(processedZip, attachment, attachmentCounter, docUUID, tbMessageId, tbMessageBigAttrId, attachUUID);
	}

	/**
	 * Метод сохраняет вложение, представленное gzip-архивом. При этом в blob поле сохраняется архив,
	 * полученный путем преобразования исходного выбранного архива на диске.
	 * Исходный архив распаковывается во временный каталог, в его единственном файле заменяются шаблоны $doc_uuid/$uuid,
	 * далее файл сохраняется во временном каталоге в Base64. Далее поизводится упаковка полученного таким путем временного файла,
	 * предватирельно раскодированного из Base64, в новый временный gzip-архив, который и передается как поток для чтения в JDBC insert.
	 *
	 * @param attachment         - объект, инкапсулирующий вложение выбранное в UI "Список загружаемых вложений" (DTO, переданный из GUI слоя).
	 * @param attachmentCounter  - номер вложения (ordinal_number в таблице tb_message_big_attributes).
	 * @param docUUID            - docGUID документа, по которому осуществляется связь с вложениями.
	 * @param tbMessageId        - идентификатор записи в таблице tb_message, т.е. айди документа.
	 * @param tbMessageBigAttrId - идентификатор записи в таблице tb_message_big_attributes, т.е. айди вложения.
	 */
	@SneakyThrows
	private void storeGzipAttachment(AttachmentFile attachment,
	                                 int attachmentCounter,
	                                 String docUUID,
	                                 long tbMessageId,
	                                 long tbMessageBigAttrId) {

		val attachUUID = UUID.randomUUID().toString().toUpperCase();
		File processedZip = AttachmentProcessor.processGzipAttachment(attachment, attachUUID, docUUID);

		saveAttachmentAsBlob(processedZip, attachment, attachmentCounter, docUUID, tbMessageId, tbMessageBigAttrId, attachUUID);
	}

	/**
	 * Метод сохраняет вложение, представленное xml-файлом. При этом в blob поле сохраняется файл,
	 * полученный путем преобразования исходного выбранного файла на диске.
	 * Исходный файл подвергается обработке, в ходе которой заменяются шаблоны $doc_uuid/$uuid,
	 * далее файл сохраняется во временном каталоге в Base64. В JDBC insert передается поток для чтения
	 * из раскодированного Base64 временного файла.
	 *
	 * @param attachment         - объект, инкапсулирующий вложение выбранное в UI "Список загружаемых вложений" (DTO, переданный из GUI слоя).
	 * @param attachmentCounter  - номер вложения (ordinal_number в таблице tb_message_big_attributes).
	 * @param docUUID            - docGUID документа, по которому осуществляется связь с вложениями.
	 * @param tbMessageId        - идентификатор записи в таблице tb_message, т.е. айди документа.
	 * @param tbMessageBigAttrId - идентификатор записи в таблице tb_message_big_attributes, т.е. айди вложения.
	 */
	@SneakyThrows
	private void storeXmlAttachment(AttachmentFile attachment,
	                                int attachmentCounter,
	                                String docUUID,
	                                long tbMessageId,
	                                long tbMessageBigAttrId) {

		val attachUUID = UUID.randomUUID().toString().toUpperCase();
		File processedFile = AttachmentProcessor.processXmlAttachment(attachment, attachUUID, docUUID);

		saveAttachmentAsBlob(processedFile, attachment, attachmentCounter, docUUID, tbMessageId, tbMessageBigAttrId, attachUUID);
	}

	/**
	 * Метод сохраняет файл в blob- поле таблицы tb_message.
	 *
	 * @param tbMessageId  - идентификатор записи в таблице tb_message, т.е. айди документа.
	 * @param documentFile - файл документа.
	 */
	@SneakyThrows
	private void saveDocumentAsBlob(long tbMessageId, File documentFile) {
		try {
			Connection connection = OracleConnectionManager.getInstance().getConnection();
			PreparedStatement query = connection.prepareStatement(
					"insert into tb_message(" +
					"id, sys_from_code, sys_to_code, priority, queue_name, time_create, time_accept, time_finish, desc_message, status, xml) values(" +
					"?, 2, 1, 100, \'TestQueue\', ?, ?, ?, \'Тестовое сообщение\', 1, ?)");

			Timestamp timestamp = new Timestamp(new Date().getTime());

			query.setLong(1, tbMessageId);
			query.setTimestamp(2, timestamp);
			query.setTimestamp(3, timestamp);
			query.setTimestamp(4, timestamp);

			// С текущей версией драйвера необходимо выполнять преобразование размера файла long к int
			// (другие версии драйвера не совместимы с текущей версией Oracle RDBMS).
			// Т.о., максимальный размер файла, который может быть обработан, составляет 2 147 483 647 bytes == 1.9 Gb.
			// Переданный InputStream закрывает драйвер JDBC (альтернатива байтовому массиву, занимает небольшой буфер в ОЗУ).
			query.setBinaryStream(5, new FileInputStream(documentFile), (int) documentFile.length());
			query.execute();

			OracleConnectionManager.getInstance().commitChanges();

			uiLogger.log("Документ сформирован <font color=\"#138808\"><b>успешно</b></font><br/>");
			uiLogger.log("<font color=\"#120A8F\"><b>[генерация документа завершена]</b></font><br/><br/>");

		} catch (SQLException e) {
			log.error("Не удалось сохранить XML документ. Исключение: ", e);
			uiLogger.log("<b><font color=\"#FF1832\">Не удалось выполнить запрос к БД</font></b><br/>");
		} finally {
			OracleConnectionManager.getInstance().closeConnection();
		}
	}

	/**
	 * Метод сохраняет файл вложения в blob- поле таблицы tb_message_big_attributes.
	 * <p>
	 * Если вложение слишком велико (порядка 1Gb), может выбрасываться исключение -
	 * "Не удалось сохранить вложение СБО_001А5729_351А572915005263_20220524131037.xml к документу. Исключение:
	 * java.sql.SQLException: ORA-01691: невозможно расширить сегмент lob ABYKOV.SYS_LOB0000175167C00006$$ на 1024 в разделе ABYKOV_TS".
	 * at oracle.jdbc.driver.DatabaseError.throwSqlException(DatabaseError.java:112)
	 * at oracle.jdbc.driver.T4CTTIoer.processError(T4CTTIoer.java:331)
	 * at oracle.jdbc.driver.T4CTTIoer.processError(T4CTTIoer.java:288)
	 * at oracle.jdbc.driver.T4C8Oall.receive(T4C8Oall.java:745)
	 * at oracle.jdbc.driver.T4CPreparedStatement.doOall8(T4CPreparedStatement.java:219)
	 * at oracle.jdbc.driver.T4CPreparedStatement.executeForRows(T4CPreparedStatement.java:970)
	 * at oracle.jdbc.driver.OracleStatement.doExecuteWithTimeout(OracleStatement.java:1190)
	 * at oracle.jdbc.driver.OraclePreparedStatement.executeInternal(OraclePreparedStatement.java:3370)
	 * at oracle.jdbc.driver.OraclePreparedStatement.execute(OraclePreparedStatement.java:3476)
	 * at org.apache.commons.dbcp.DelegatingPreparedStatement.execute(DelegatingPreparedStatement.java:256)
	 * ...
	 * <p>
	 * Данное исключение не является следствием некорректной работы приложения (в отличие от OutOfMemory). Если действительно имеют место быть
	 * требования хранить гигабайтные файлы в БД - следует обратиться к администраторам с запросом расширить сегмент.
	 *
	 * @param processedFile      - обработанный и декодированный временный файл-копия вложения.
	 * @param attachment         - объект, инкапсулирующий вложение выбранное в UI "Список загружаемых вложений" (DTO, переданный из GUI слоя).
	 * @param attachmentCounter  - номер вложения (ordinal_number в таблице tb_message_big_attributes).
	 * @param docUUID            - docGUID документа, по которому осуществляется связь с вложениями.
	 * @param tbMessageId        - идентификатор записи в таблице tb_message, т.е. айди документа.
	 * @param tbMessageBigAttrId - идентификатор записи в таблице tb_message_big_attributes, т.е. айди вложения.
	 * @param attachUUID         - random UUID данного конкретного вложения, на него заменяется шаблон $uuid.
	 */
	private void saveAttachmentAsBlob(File processedFile,
	                                  AttachmentFile attachment,
	                                  int attachmentCounter,
	                                  String docUUID,
	                                  long tbMessageId,
	                                  long tbMessageBigAttrId,
	                                  String attachUUID) throws FileNotFoundException {
		try {
			Connection connection = OracleConnectionManager.getInstance().getConnection();
			PreparedStatement query = connection.prepareStatement(
					"insert into tb_message_big_attributes(" +
					"id, tb_msg_id, type, name, content_type, description, body, ordinal_number, doc_guid, att_guid) values (" +
					"?, ?, \'ATC\', ?, ?, \'Test description\', ?, ?, ?, ?)");

			query.setLong(1, tbMessageBigAttrId);
			query.setLong(2, tbMessageId);
			query.setString(3, attachment.getName());
			query.setString(4, attachment.getContentType());

			// С текущей версией драйвера необходимо выполнять преобразование к int.
			// (другие версии драйвера не совместимы с текущей версией Oracle RDBMS).
			// Т.о., максимальный размер файла, который может быть обработан, составляет 2 147 483 647 bytes == 1.9 Gb.
			// Переданный InputStream закрывает драйвер JDBC.
			// Вместо setBytes используем setBinaryStream, чтобы не хранить байты в ОЗУ.
			query.setBinaryStream(5, new FileInputStream(processedFile), (int) processedFile.length());

			query.setInt(6, attachmentCounter);

			query.setString(7, docUUID);
			query.setString(8, attachUUID);
			query.execute();

			OracleConnectionManager.getInstance().commitChanges();

			uiLogger.log("Вложение к документу сформировано <font color=\"#138808\"><b>успешно</b></font><br/>");

		} catch (SQLException e) {
			log.error("Не удалось сохранить вложение {} к документу. Исключение: ", attachment.getName(), e);
			uiLogger.log("<b><font color=\"#FF1832\">Не удалось выполнить запрос к БД</font></b><br/>");
		} finally {
			OracleConnectionManager.getInstance().closeConnection();
		}
	}

	/**
	 * Метод получает идентификатор документа для последующей записи в tb_message.
	 * Производится извлечение из SQL sequence sq_tb_message.
	 *
	 * @return - идентификатор документа для последующей записи в tb_message.
	 */
	private long getNexTbMessageId() {
		try {
			Connection connection = OracleConnectionManager.getInstance().getConnection();
			PreparedStatement query = connection.prepareStatement("select sq_tb_message.nextval from dual");
			ResultSet resultSet = query.executeQuery();

			resultSet.next();
			return resultSet.getLong(NumberUtils.INTEGER_ONE);
		} catch (SQLException e) {
			log.error("Не удалось получить идентификатор для документа из последовательности sq_tb_message.nextval. Исключение: ", e);
			uiLogger.log("<b><font color=\"#FF1832\">Не удалось выполнить запрос к БД</font></b><br/>");

			throw new RuntimeException(e);

		} finally {
			OracleConnectionManager.getInstance().closeConnection();
		}
	}

	/**
	 * Метод получает идентификатор вложения для последующей записи в tb_message_big_attributes.
	 * Производится извлечение из SQL sequence sq_tb_message_big_attributes.
	 *
	 * @return - идентификатор вложения для последующей записи в tb_message_big_attributes.
	 */
	private long getNextTbMessageBigAttrId() {
		try {
			val connection = OracleConnectionManager.getInstance().getConnection();
			val query = connection.prepareStatement("select sq_tb_message_big_attributes.nextval from dual");
			val resultSet = query.executeQuery();

			resultSet.next();
			return resultSet.getLong(NumberUtils.INTEGER_ONE);
		} catch (SQLException e) {
			log.error("Не удалось получить идентификатор для вложения из последовательности sq_tb_message_big_attributes.nextval. Исключение: ", e);
			uiLogger.log("<b><font color=\"#FF1832\">Не удалось выполнить запрос к БД</font></b><br/>");

			throw new RuntimeException(e);
		} finally {
			OracleConnectionManager.getInstance().closeConnection();
		}
	}
}
