package alekseybykov.pets.mg.dao.mappers.tbmessage;

import alekseybykov.pets.mg.core.businessobjects.PageableData;
import alekseybykov.pets.mg.core.businessobjects.pageable.TBMessageTable;
import lombok.val;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author bykov.alexey
 * @since 04.07.2021
 */
@Component
public class TBMessageRowMapper implements RowMapper<PageableData> {

	@Override
	public PageableData mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
		val sysFromCode = resultSet.getInt("sys_from_code");
		val sysToCode = resultSet.getInt("sys_to_code");
		return TBMessageTable.builder()
				.id(resultSet.getString("id"))
				.routeMarker(evalRouteMarker(sysFromCode, sysToCode))
				.sysFromCode(sysFromCode)
				.sysToCode(sysToCode)
				.priority(resultSet.getInt("priority"))
				.queueName(resultSet.getString("queue_name"))
				.description(resultSet.getString("desc_message"))
				.timeCreate(resultSet.getTimestamp("time_create"))
				.timeAccept(resultSet.getTimestamp("time_accept"))
				.timeFinish(resultSet.getTimestamp("time_finish"))
				.status(resultSet.getInt("status"))
				.errorCode(resultSet.getString("error_code"))
				.errorText(resultSet.getString("error_text"))
				.timeLastProcessed(resultSet.getTimestamp("time_last_processed"))
				.processedCount(resultSet.getInt("processed_count"))
				.build();
	}

	private String evalRouteMarker(int sysFromCode, int sysToCode) {
		if (sysFromCode == 1 && sysToCode == 2) {
			return "--> OeBS";
		} else {
			return "OeBS -->";
		}
	}
}
