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

@Slf4j
@Repository
public class OEBSImportImpl implements OEBSImportDao {

	private static final UILogger uiLogger = UILogger.getInstance();

	@Override
	@SneakyThrows
	public void importOagisFileWithAttachmentFiles(
			@NotNull OAGISFile oagisFile,
			@NotNull List<AttachmentFile> attachmentFiles
	) {
		var tbMessageId = getNexTbMessageId();
		val docUUID = UUID.randomUUID().toString().toUpperCase();

		storeAttachments(tbMessageId, docUUID, attachmentFiles);
		storeXmlDocument(oagisFile, tbMessageId, docUUID);
	}

	@SneakyThrows
	private void storeXmlDocument(
			OAGISFile oagisFile,
			long tbMessageId,
			String docUUID
	) {
		val msgFilePath = oagisFile.getPath();

		uiLogger.log("<font color=\"#120A8F\"><b>[генерация документа]</b></font><br/>");
		uiLogger.log("Будет сгенерирован документ с идентификатором = [<b>" + tbMessageId + "</b>]<br/>");
		uiLogger.log("Файл документа = [<b>" + msgFilePath + "</b>]<br/>");

		File processedFile = TemplateProcessor.process(oagisFile, docUUID);

		File base64DecodedFile = EncodeUtils.decodeFile(processedFile, EncodingScheme.BASE64);

		saveDocumentAsBlob(tbMessageId, base64DecodedFile);
	}

	@SneakyThrows
	private void storeAttachments(
			long tbMessageId,
			String docUUID,
			List<AttachmentFile> attachments
	) {
		if (attachments.isEmpty()) {
			uiLogger.log(
					"<font color=\"#120A8F\"><b>[генерация вложений к документу не производится " +
							"(список вложений пустой)]</b></font><br/><br/>"
			);
			return;
		}

		uiLogger.log("<font color=\"#120A8F\"><b>[генерация вложений к документу]</b></font>");

		for (var attachmentCounter = 0; attachmentCounter < attachments.size(); attachmentCounter++) {
			AttachmentFile attachment = attachments.get(attachmentCounter);
			File attachmentFile = new File(attachment.getPath());

			var tbMessageBigAttrId = getNextTbMessageBigAttrId();

			uiLogger.log(
					"Генерируется вложение с ID = [<b>" +
							tbMessageBigAttrId + "</b>] к документу с ID = [<b>" +
							tbMessageId + "</b>]<br/>"
			);

			val attachName = attachment.getName();
			val attachPath = attachment.getPath();

			uiLogger.log("Файл вложения = [<b>" + attachPath + "</b>]<br/>");
			uiLogger.log(
					"Имя вложения = [<b>" + attachName + "</b>] "
							+ makeAttachDetailsLogString(attachPath, attachName)
			);

			if (ZipUtils.isZipArchive(attachmentFile)) {
				storeZipAttachment(
						attachment,
						attachmentCounter,
						docUUID,
						tbMessageId,
						tbMessageBigAttrId
				);
			} else if (GzipUtils.isGzipArchive(attachmentFile)) {
				storeGzipAttachment(
						attachment,
						attachmentCounter,
						docUUID,
						tbMessageId,
						tbMessageBigAttrId
				);
			} else {
				storeXmlAttachment(
						attachment,
						attachmentCounter,
						docUUID,
						tbMessageId,
						tbMessageBigAttrId
				);
			}
		}
		uiLogger.log(
				"<font color=\"#120A8F\"><b>[генерация вложений к документу завершена]</b></font><br/><br/>"
		);
	}

	private String makeAttachDetailsLogString(String attachPath, String attachName) {
		return StringUtils.equalsIgnoreCase(FilenameUtils.getName(attachPath), attachName)
		       ? "<br/>"
		       : "(вложение передается c новым именем)<br/>";
	}

	@SneakyThrows
	private void storeZipAttachment(
			AttachmentFile attachment,
			int attachmentCounter,
			String docUUID,
			long tbMessageId,
			long tbMessageBigAttrId
	) {
		val attachUUID = UUID.randomUUID().toString().toUpperCase();
		File processedZip = AttachmentProcessor.processZipAttachment(attachment, attachUUID, docUUID);

		saveAttachmentAsBlob(
				processedZip,
				attachment,
				attachmentCounter,
				docUUID,
				tbMessageId,
				tbMessageBigAttrId,
				attachUUID
		);
	}

	@SneakyThrows
	private void storeGzipAttachment(
			AttachmentFile attachment,
			int attachmentCounter,
			String docUUID,
			long tbMessageId,
			long tbMessageBigAttrId
	) {
		val attachUUID = UUID.randomUUID().toString().toUpperCase();
		File processedZip = AttachmentProcessor.processGzipAttachment(attachment, attachUUID, docUUID);

		saveAttachmentAsBlob(
				processedZip,
				attachment,
				attachmentCounter,
				docUUID,
				tbMessageId,
				tbMessageBigAttrId,
				attachUUID
		);
	}

	@SneakyThrows
	private void storeXmlAttachment(
			AttachmentFile attachment,
			int attachmentCounter,
			String docUUID,
			long tbMessageId,
			long tbMessageBigAttrId
	) {
		val attachUUID = UUID.randomUUID().toString().toUpperCase();
		File processedFile = AttachmentProcessor.processXmlAttachment(attachment, attachUUID, docUUID);

		saveAttachmentAsBlob(
				processedFile,
				attachment,
				attachmentCounter,
				docUUID,
				tbMessageId,
				tbMessageBigAttrId,
				attachUUID
		);
	}

	@SneakyThrows
	private void saveDocumentAsBlob(long tbMessageId, File documentFile) {
		try {
			Connection connection = OracleConnectionManager.getInstance().getConnection();
			PreparedStatement query = connection.prepareStatement(
					"insert into tb_message(" +
					"id, sys_from_code, sys_to_code, priority, queue_name, time_create, time_accept, time_finish, " +
							"desc_message, status, xml) values(" +
					"?, 2, 1, 100, \'TestQueue\', ?, ?, ?, \'Тестовое сообщение\', 1, ?)");

			Timestamp timestamp = new Timestamp(new Date().getTime());

			query.setLong(1, tbMessageId);
			query.setTimestamp(2, timestamp);
			query.setTimestamp(3, timestamp);
			query.setTimestamp(4, timestamp);

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

	private void saveAttachmentAsBlob(
			File processedFile,
			AttachmentFile attachment,
			int attachmentCounter,
			String docUUID,
			long tbMessageId,
			long tbMessageBigAttrId,
			String attachUUID
	) throws FileNotFoundException {
		try {
			Connection connection = OracleConnectionManager.getInstance().getConnection();
			PreparedStatement query = connection.prepareStatement(
					"insert into tb_message_big_attributes(" +
					"id, tb_msg_id, type, name, content_type, description, body, ordinal_number, " +
							"doc_guid, att_guid) values (" +
					"?, ?, \'ATC\', ?, ?, \'Test description\', ?, ?, ?, ?)");

			query.setLong(1, tbMessageBigAttrId);
			query.setLong(2, tbMessageId);
			query.setString(3, attachment.getName());
			query.setString(4, attachment.getContentType());

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

	private long getNexTbMessageId() {
		try {
			Connection connection = OracleConnectionManager.getInstance().getConnection();
			PreparedStatement query = connection.prepareStatement("select sq_tb_message.nextval from dual");
			ResultSet resultSet = query.executeQuery();

			resultSet.next();
			return resultSet.getLong(NumberUtils.INTEGER_ONE);
		} catch (SQLException e) {
			log.error(
					"Не удалось получить идентификатор для документа из последовательности sq_tb_message.nextval. " +
							"Исключение: ", e
			);
			uiLogger.log("<b><font color=\"#FF1832\">Не удалось выполнить запрос к БД</font></b><br/>");

			throw new RuntimeException(e);

		} finally {
			OracleConnectionManager.getInstance().closeConnection();
		}
	}

	private long getNextTbMessageBigAttrId() {
		try {
			val connection = OracleConnectionManager.getInstance().getConnection();
			val query = connection.prepareStatement("select sq_tb_message_big_attributes.nextval from dual");
			val resultSet = query.executeQuery();

			resultSet.next();
			return resultSet.getLong(NumberUtils.INTEGER_ONE);
		} catch (SQLException e) {
			log.error(
					"Не удалось получить идентификатор для вложения из последовательности " +
							"sq_tb_message_big_attributes.nextval. Исключение: ", e);
			uiLogger.log("<b><font color=\"#FF1832\">Не удалось выполнить запрос к БД</font></b><br/>");

			throw new RuntimeException(e);
		} finally {
			OracleConnectionManager.getInstance().closeConnection();
		}
	}
}
