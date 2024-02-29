package alekseybykov.pets.mg.dao.mappers.tbbigattr;

import alekseybykov.pets.mg.core.businessobjects.PageableData;
import alekseybykov.pets.mg.core.businessobjects.pageable.TBMessageBigAttributesTable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author bykov.alexey
 * @since 04.07.2021
 */
@Component
public class TBMessageBigAttributesRowMapper implements RowMapper<PageableData> {

	@Override
	public PageableData mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
		return TBMessageBigAttributesTable.builder()
				.tbMsgId(resultSet.getInt("tb_msg_id"))
				.type(resultSet.getString("type"))
				.name(resultSet.getString("name"))
				.contentType(resultSet.getString("content_type"))
				.description(resultSet.getString("description"))
				.ordinalNumber(resultSet.getInt("ordinal_number"))
				.docGuid(resultSet.getString("doc_guid"))
				.id(resultSet.getInt("id"))
				.attGuid(resultSet.getString("att_guid"))
				.preparedDate(resultSet.getTimestamp("prepared_date"))
				.build();
	}
}
