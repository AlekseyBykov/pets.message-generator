package alekseybykov.pets.mg.dao.tbmessage.impl;

import alekseybykov.pets.mg.core.businessobjects.PageableData;
import alekseybykov.pets.mg.core.db.oracle.OracleConnectionManager;
import alekseybykov.pets.mg.dao.mappers.tbmessage.TBMessageBlobMapper;
import alekseybykov.pets.mg.dao.mappers.tbmessage.TBMessageRowMapper;
import alekseybykov.pets.mg.dao.tbmessage.TBMessageDao;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import lombok.var;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.charset.Charset;

@Slf4j
@Repository
public class TBMessageDaoImpl implements TBMessageDao {

	@Autowired
	private TBMessageRowMapper rowMapper;
	@Autowired
	private TBMessageBlobMapper blobMapper;

	@Override
	public PageableData fetchRowById(int id) {
		String sqlText = "\nselect * from tb_message where id = ?\n\n";

		PageableData row = null;
		try {
			JdbcTemplate jdbcTemplate = OracleConnectionManager.getInstance().getJdbcTemplate();
			row = jdbcTemplate.queryForObject(
					sqlText,
					new Object[] {id},
					rowMapper
			);
		} catch (DataAccessException e) {
			log.error(
					"Не удалось найти запись таблицы TB_MESSAGE по идентификатору " + id +
							". SQL запрос: " + sqlText +
							" Исключение: ", e
			);
		}
		return row;
	}

	@Override
	public String fetchFileById(
			int id,
			Charset charset
	) {
		var result = StringUtils.EMPTY;
		val sqlText = "\nselect xml from tb_message where id = ?\n\n";

		try {
			val jdbcTemplate = OracleConnectionManager.getInstance().getJdbcTemplate();

			byte[] blobBytes = jdbcTemplate.queryForObject(
					sqlText,
					new Object[] {id},
					blobMapper
			);
			result = new String(blobBytes, charset);
		} catch (DataAccessException e) {
			log.error(
					"Не удалось извлечь сообщение OAGIS из TB_MESSAGE по идентификатору " + id +
							". SQL запрос: " + sqlText +
							" Исключение: ", e
			);
		}
		return result;
	}
}
