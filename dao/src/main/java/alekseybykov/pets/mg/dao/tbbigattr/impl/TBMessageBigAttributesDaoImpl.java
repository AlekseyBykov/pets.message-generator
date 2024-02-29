package alekseybykov.pets.mg.dao.tbbigattr.impl;

import alekseybykov.pets.mg.core.businessobjects.PageableData;
import alekseybykov.pets.mg.core.db.oracle.OracleConnectionManager;
import alekseybykov.pets.mg.dao.mappers.tbbigattr.TBMessageBigAttributesBlobMapper;
import alekseybykov.pets.mg.dao.mappers.tbbigattr.TBMessageBigAttributesRowMapper;
import alekseybykov.pets.mg.dao.tbbigattr.TBMessageBigAttributesDao;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.UnsupportedEncodingException;

/**
 * @author bykov.alexey
 * @since 15.04.2021
 */
@Slf4j
@Repository
public class TBMessageBigAttributesDaoImpl implements TBMessageBigAttributesDao {

	@Autowired
	private TBMessageBigAttributesRowMapper rowMapper;
	@Autowired
	private TBMessageBigAttributesBlobMapper blobMapper;

	@Override
	public PageableData fetchRowById(int id) {
		val sqlText = "\nselect * from tb_message_big_attributes where id = ?\n\n";

		PageableData row = null;
		try {
			JdbcTemplate jdbcTemplate = OracleConnectionManager.getInstance().getJdbcTemplate();
			row = jdbcTemplate.queryForObject(sqlText, new Object[] {id}, rowMapper);
		} catch (DataAccessException e) {
			log.error("Не удалось найти запись таблицы TB_MESSAGE_BIG_ATTRIBUTES по идентификатору " + id + ". SQL запрос: " + sqlText + " Исключение: ", e);
		} finally {
			OracleConnectionManager.getInstance().closeConnection();
		}
		return row;
	}

	@Override
	public String fetchFileById(int id, String charsetName) {
		String result = StringUtils.EMPTY;

		val sqlText = "\nselect * from tb_message_big_attributes where id = ?\n\n";

		try {
			JdbcTemplate jdbcTemplate = OracleConnectionManager.getInstance().getJdbcTemplate();

			// Получаем обработанные и декодированные байты из blob-поля. Base64 декодирование было выполнено ранее, при записи.
			// Если размер запрашиваемого blob'а превышает ONE_HUNDRED_KB, данный массив будет содержать байты информирующего сообщения.
			byte[] blobBytes = jdbcTemplate.queryForObject(sqlText, new Object[] {id}, blobMapper);

			// Отображаем файл пользователю в виде String, в зависимости от выбранной им в UI кодировки.
			// Т.о., изначальная кодировка нам не известна, но может быть восстановлена из заданного в UI набора, если в процессе преобразований
			// (поиск и замена шаблонов $uuid/$doc_uuid, запись во временные файлы, чтение из временных файлов) не производилась запись байт
			// в любой кодировке (этого мы избежали, используя Base64).
			result = new String(blobBytes, charsetName);

		} catch (DataAccessException | UnsupportedEncodingException e) {
			log.error("Не удалось извлечь вложение TB_MESSAGE_BIG_ATTRIBUTES по идентификатору " + id + ". SQL запрос: " + sqlText + " Исключение: ", e);
		} finally {
			OracleConnectionManager.getInstance().closeConnection();
		}

		return result;
	}
}
