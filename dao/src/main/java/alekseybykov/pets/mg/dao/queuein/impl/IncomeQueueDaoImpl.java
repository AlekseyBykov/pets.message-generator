package alekseybykov.pets.mg.dao.queuein.impl;

import alekseybykov.pets.mg.core.businessobjects.PageableData;
import alekseybykov.pets.mg.core.db.oracle.OracleConnectionManager;
import alekseybykov.pets.mg.dao.mappers.queuein.IncomeQueueRowMapper;
import alekseybykov.pets.mg.dao.queuein.IncomeQueueDao;
import lombok.SneakyThrows;
import lombok.val;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.Blob;
import java.sql.ResultSet;

@Repository
public class IncomeQueueDaoImpl implements IncomeQueueDao {

	private static final Logger logger = LoggerFactory.getLogger(IncomeQueueDaoImpl.class);

	@Autowired
	private IncomeQueueRowMapper rowMapper;
	/*@Autowired
	private IncomeQueueBlobMapper blobMapper;*/

	@Override
	public PageableData fetchRowById(int id) {
		PageableData row = null;

		val sqlText = "\nselect * from queue_packet_in where id = ?\n\n";

		try {
			JdbcTemplate jdbcTemplate = OracleConnectionManager.getInstance().getJdbcTemplate();
			row = jdbcTemplate.queryForObject(sqlText, new Object[] {id}, rowMapper);
		} catch (DataAccessException e) {
			logger.error(
					"Не удалось найти запись таблицы QUEUE_PACKET_IN по идентификатору " + id +
							". SQL запрос: " + sqlText +
							" Исключение: ", e
			);
		}
		return row;
	}

	@Override
	public String fetchFileById(int id, String charsetName) {
		val sqlText = "\nselect blobcontent from queue_packet_in where blobcontent is not null and id = ?\n\n";
		JdbcTemplate jdbcTemplate = OracleConnectionManager.getInstance().getJdbcTemplate();
		return jdbcTemplate.query(sqlText, new Object[]{id}, new ResultSetExtractor<String>() {
			@SneakyThrows
			@Override
			public String extractData(ResultSet rs) {
				if (rs.next()) {
					Blob column = rs.getBlob("blobcontent");
					byte[] blobBytes = column.getBytes(NumberUtils.INTEGER_ONE, (int) column.length());
					return new String(blobBytes, charsetName);
				} else {
					return "Файл не найден";
				}
			}
		});
	}
}
