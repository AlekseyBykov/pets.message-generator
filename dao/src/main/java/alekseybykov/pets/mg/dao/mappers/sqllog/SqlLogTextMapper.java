package alekseybykov.pets.mg.dao.mappers.sqllog;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class SqlLogTextMapper implements RowMapper<byte[]> {

	@Override
	public byte[] mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
		return resultSet.getBytes("sql_text");
	}
}
