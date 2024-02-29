package alekseybykov.pets.mg.dao.mappers.sqllog;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author bykov.alexey
 * @since 26.06.2022
 */
@Component
public class SqlLogTextMapper implements RowMapper<byte[]> {

	@Override
	public byte[] mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
		return resultSet.getBytes("sql_text");
	}
}
