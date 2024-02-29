package alekseybykov.pets.mg.dao.mappers.queueout;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author bykov.alexey
 * @since 04.07.2021
 */
@Component
public class OutcomeQueueBlobMapper implements RowMapper<byte[]> {

	@Override
	public byte[] mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
		Blob column = resultSet.getBlob("blobcontent");
		return column.getBytes(NumberUtils.INTEGER_ONE, (int) column.length());
	}
}
