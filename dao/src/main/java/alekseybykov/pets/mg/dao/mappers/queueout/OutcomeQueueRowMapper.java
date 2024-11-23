package alekseybykov.pets.mg.dao.mappers.queueout;

import alekseybykov.pets.mg.core.businessobjects.PageableData;
import alekseybykov.pets.mg.core.businessobjects.pageable.OutcomeQueueTable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class OutcomeQueueRowMapper implements RowMapper<PageableData> {

	@Override
	public PageableData mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
		return OutcomeQueueTable.builder()
				.id(resultSet.getInt("id"))
				.url(resultSet.getString("url"))
				.createDate(resultSet.getTimestamp("createdate"))
				.seqNum(resultSet.getInt("seqnum"))
				.sequenceSize(resultSet.getInt("sequencesize"))
				.guid(resultSet.getString("guid"))
				.seqGuid(resultSet.getString("seqguid"))
				.fileSize(resultSet.getInt("file_size"))
				.contentClassname(resultSet.getString("contentclassname"))
				.status(resultSet.getString("status"))
				.errorMessage(resultSet.getString("errormessage"))
				.errorCode(resultSet.getString("errorcode"))
				.toComplexId(resultSet.getString("to_complex_id"))
				.changeStatusDate(resultSet.getTimestamp("change_status_date"))
				.priority(resultSet.getInt("priority"))
				.ownerDocsCount(resultSet.getInt("owner_docs_count"))
				.processCount(resultSet.getInt("processcount"))
				.info(resultSet.getString("info"))
				.build();
	}
}
