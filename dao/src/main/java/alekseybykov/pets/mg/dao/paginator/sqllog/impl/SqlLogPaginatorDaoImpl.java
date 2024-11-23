package alekseybykov.pets.mg.dao.paginator.sqllog.impl;

import alekseybykov.pets.mg.core.businessobjects.PageableData;
import alekseybykov.pets.mg.core.db.oracle.OracleConnectionManager;
import alekseybykov.pets.mg.dao.mappers.sqllog.SqlLogRowMapper;
import alekseybykov.pets.mg.dao.paginator.sqllog.SqlLogPaginatorDao;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class SqlLogPaginatorDaoImpl implements SqlLogPaginatorDao {

	@Autowired
	private SqlLogRowMapper rowMapper;

	@Override
	public List<PageableData> fetchRowsRange(int startRownum, int endRownum) {
		List<PageableData> rows = new ArrayList<>();

		val sqlText = "\nselect \n" +
		              "   rs.* \n" +
		              "from\n" +
		              "   (\n" +
		              "      select \n" +
		              "           sql_id,\n" +
		              "           first_load_time,\n" +
		              "           username,\n" +
		              "           parsing_schema_name,\n" +
		              "           service,\n" +
		              "           module,\n" +
		              "           sql_text,\n" +
		              "           disk_reads,\n" +
		              "           rows_processed,\n" +
		              "           elapsed_time, \n" +
		              "           row_number() over (order by first_load_time desc) as row_num\n" +
		              "      from \n" +
		              "         v$sql t left join dba_users u on t.parsing_user_id = u.user_id " +
		              "      where to_date(first_load_time, 'YYYY-MM-DD hh24:mi:ss') > add_months(trunc(sysdate,'MM'), -2)\n" +
		              "   ) rs\n" +
		              "where \n" +
		              "   rs.row_num >= ? and rs.row_num  <= ?\n" +
		              "order by \n" +
		              "   rs.row_num\n\n";
		try {
			JdbcTemplate jdbcTemplate = OracleConnectionManager.getInstance().getJdbcTemplate();
			rows = jdbcTemplate.query(sqlText, new Object[] {startRownum, endRownum}, rowMapper);
		} catch (DataAccessException e) {
			log.error("Не удалось извлечь данные V$SQL. SQL запрос: " + sqlText + " Исключение: ", e);
		}

		return rows;
	}

	@Override
	public int fetchRowsCount() {
		int rowsCount = NumberUtils.INTEGER_ZERO;

		val sqlText = "\nselect count(*) from v$sql\n\n";

		try {
			JdbcTemplate jdbcTemplate = OracleConnectionManager.getInstance().getJdbcTemplate();
			rowsCount = jdbcTemplate.queryForObject(sqlText, Integer.class);
		} catch (DataAccessException e) {
			log.error("Не удалось найти число строк таблицы V$SQL. SQL запрос: " + sqlText + " Исключение: ", e);
		}

		return rowsCount;
	}
}
