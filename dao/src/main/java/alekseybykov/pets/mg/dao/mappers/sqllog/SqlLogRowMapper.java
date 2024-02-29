package alekseybykov.pets.mg.dao.mappers.sqllog;

import alekseybykov.pets.mg.core.businessobjects.PageableData;
import alekseybykov.pets.mg.core.businessobjects.pageable.SqlLogRow;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author bykov.alexey
 * @since 26.06.2022
 */
@Component
public class SqlLogRowMapper implements RowMapper<PageableData> {

	@Override
	public PageableData mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
		return SqlLogRow.builder()
				.sqlId(resultSet.getString("sql_id"))
				.firstLoadTime(resultSet.getString("first_load_time"))
				/*.parsingUserId(resultSet.getInt("parsing_user_id"))*/
				.userName(resultSet.getString("username"))
				.parsingSchemaName(resultSet.getString("parsing_schema_name"))
				.service(resultSet.getString("service"))
				.module(resultSet.getString("module"))
				.sqlText(resultSet.getString("sql_text"))
				.discReads(resultSet.getInt("disk_reads"))
				.rowsProcessed(resultSet.getInt("rows_processed"))
				.elapsedTime(resultSet.getInt("elapsed_time"))
				.build();
	}
}
