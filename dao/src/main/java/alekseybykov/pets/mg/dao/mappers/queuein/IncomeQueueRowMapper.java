package alekseybykov.pets.mg.dao.mappers.queuein;

import alekseybykov.pets.mg.core.businessobjects.PageableData;
import alekseybykov.pets.mg.core.businessobjects.pageable.IncomeQueueTable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author bykov.alexey
 * @since 04.07.2021
 */
@Component
public class IncomeQueueRowMapper implements RowMapper<PageableData> {

	@Override
	public PageableData mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
		return IncomeQueueTable.builder()
				.id(resultSet.getInt("id"))
				.fileName(resultSet.getString("filename"))
				.receiveDate(resultSet.getTimestamp("receivedate"))
				.seqGuid(resultSet.getString("seqguid"))
				.fileSize(resultSet.getInt("file_size"))
				.errorMessage(resultSet.getString("errormessage"))
				.status(resultSet.getString("status"))
				.complexType(resultSet.getString("complextype"))
				.exported(resultSet.getInt("exported"))
				.errorCode(resultSet.getString("errorcode"))
				.changeStatusDate(resultSet.getTimestamp("change_status_date"))
				.priority(resultSet.getInt("priority"))
				.processCount(resultSet.getInt("processcount"))
				.ownerDocsCount(resultSet.getInt("owner_docs_count"))
				.obtainedThrough(resultSet.getString("obtained_through"))
				.info(resultSet.getString("info"))
				.recipient(resultSet.getString("recipient"))
				.sender(resultSet.getString("sender"))
				.build();
	}
}
