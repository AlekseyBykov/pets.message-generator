package alekseybykov.pets.mg.dao.sqllog.impl;

import alekseybykov.pets.mg.core.businessobjects.PageableData;
import alekseybykov.pets.mg.core.db.oracle.OracleConnectionManager;
import alekseybykov.pets.mg.dao.mappers.sqllog.SqlLogRowMapper;
import alekseybykov.pets.mg.dao.mappers.sqllog.SqlLogTextMapper;
import alekseybykov.pets.mg.dao.sqllog.SqlLogDao;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import lombok.var;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author bykov.alexey
 * @since 26.06.2022
 */
@Slf4j
@Repository
public class SqlLogDaoImpl implements SqlLogDao {

	@Autowired
	private SqlLogRowMapper sqlLogRowMapper;

	@Autowired
	private SqlLogTextMapper sqlLogTextMapper;

	@Override
	public PageableData fetchRowById(int id) {
		PageableData row = null;

		val sqlText = "\nselect * from v$sql where sql_id = ?\n\n";

		try {
			JdbcTemplate jdbcTemplate = OracleConnectionManager.getInstance().getJdbcTemplate();
			row = jdbcTemplate.queryForObject(sqlText, new Object[] {id}, sqlLogRowMapper);
		} catch (DataAccessException e) {
			log.error("Не удалось найти запись таблицы V$SQL по идентификатору " + id + ". SQL запрос: " + sqlText + " Исключение: ", e);
		}
		return row;
	}

	@Override
	public String fetchSqlTextById(String id, String charsetName) {
		var result = StringUtils.EMPTY;
		val sqlText = "\nselect sql_text from v$sql where sql_id = ?\n\n";

		try {
			JdbcTemplate jdbcTemplate = OracleConnectionManager.getInstance().getJdbcTemplate();
			byte[] sqlTextBytes = jdbcTemplate.queryForObject(sqlText, new Object[] {id}, sqlLogTextMapper);
			if (sqlTextBytes != null && sqlTextBytes.length > 0) {
				result = new String(sqlTextBytes, charsetName);
			}
		} catch (DataAccessException | UnsupportedEncodingException e) {
			log.error("Не удалось извлечь данные V$SQL по идентификатору + " + id + ". SQL запрос: " + sqlText + " Исключение: ", e);
		}
		return result;
	}

	@Override
	public List<PageableData> findAllRowsBySqlSubstring(String sqlSubstring, int startRownum, int endRownum) {
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
		              "         v$sql t left join dba_users u on t.parsing_user_id = u.user_id\n" +
		              "      where \n" +
		              "         to_date(first_load_time, 'YYYY-MM-DD hh24:mi:ss') > add_months(trunc(sysdate,'MM'), -2)\n" +
		              "         and sql_text like '%" + sqlSubstring.trim() + "%'\n" +
		              "   ) rs\n" +
		              "where \n" +
		              "   rs.row_num >= ? and rs.row_num  <= ?\n" +
		              "order by \n" +
		              "   rs.row_num\n\n";
		try {
			JdbcTemplate jdbcTemplate = OracleConnectionManager.getInstance().getJdbcTemplate();
			rows = jdbcTemplate.query(sqlText, new Object[] {startRownum, endRownum}, sqlLogRowMapper);
		} catch (DataAccessException e) {
			log.error("Не удалось извлечь данные V$SQL. SQL запрос: " + sqlText + " Исключение: ", e);
		}

		return rows;
	}
}
