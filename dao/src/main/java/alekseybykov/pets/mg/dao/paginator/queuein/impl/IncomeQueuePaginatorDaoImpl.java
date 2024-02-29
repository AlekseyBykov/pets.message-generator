package alekseybykov.pets.mg.dao.paginator.queuein.impl;

import alekseybykov.pets.mg.core.businessobjects.PageableData;
import alekseybykov.pets.mg.core.db.oracle.OracleConnectionManager;
import alekseybykov.pets.mg.dao.mappers.queuein.IncomeQueueRowMapper;
import alekseybykov.pets.mg.dao.paginator.queuein.IncomeQueuePaginatorDao;
import lombok.val;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bykov.alexey
 * @since 04.07.2021
 */
@Repository
public class IncomeQueuePaginatorDaoImpl implements IncomeQueuePaginatorDao {

	private static final Logger logger = LoggerFactory.getLogger(IncomeQueuePaginatorDaoImpl.class);

	@Autowired
	private IncomeQueueRowMapper rowMapper;

	@Override
	public List<PageableData> fetchRowsRange(int startRownum, int endRownum) {
		List<PageableData> rows = new ArrayList<>();

		val sqlText = "\nselect rs.* from\n" +
		              " (select t.*, row_number() over (order by receivedate desc) as row_num\n" +
		              " from queue_packet_in t\n" +
		              " ) rs\n" +
		              "where rs.row_num >= ? and rs.row_num  <= ?\n" +
		              "order by rs.row_num\n\n";
		try {
			JdbcTemplate jdbcTemplate = OracleConnectionManager.getInstance().getJdbcTemplate();
			rows = jdbcTemplate.query(sqlText, new Object[] {startRownum, endRownum}, rowMapper);
		} catch (DataAccessException e) {
			logger.error("Не удалось извлечь данные QUEUE_PACKET_IN. SQL запрос: " + sqlText + " Исключение: ", e);
		}
		return rows;
	}

	@Override
	public int fetchRowsCount() {
		int rowsCount = NumberUtils.INTEGER_ZERO;

		val sqlText = "\nselect count(*) from queue_packet_in\n\n";

		try {
			JdbcTemplate jdbcTemplate = OracleConnectionManager.getInstance().getJdbcTemplate();
			rowsCount = jdbcTemplate.queryForObject(sqlText, Integer.class);
		} catch (DataAccessException e) {
			logger.error("Не удалось найти число строк таблицы QUEUE_PACKET_IN. SQL запрос: " + sqlText + " Исключение: ", e);
		}
		return rowsCount;
	}
}
