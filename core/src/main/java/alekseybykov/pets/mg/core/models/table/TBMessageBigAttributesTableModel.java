package alekseybykov.pets.mg.core.models.table;

import alekseybykov.pets.mg.core.businessobjects.PageableData;
import alekseybykov.pets.mg.core.businessobjects.pageable.TBMessageBigAttributesTable;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * @author bykov.alexey
 * @since 18.03.2021
 */
@Component
public class TBMessageBigAttributesTableModel extends AbstractTableModel implements TableModel {

	private static final String[] COLUMN_NAMES = {
			"id", "tb_msg_id", "doc_guid", "att_guid", "description",
			"ordinal_number", "name", "type", "content_type", "prepared_date"
	};

	private List<PageableData> data;

	@Override
	public int getRowCount() {
		if (data == null) {
			return NumberUtils.INTEGER_ZERO;
		}
		return data.size();
	}

	@Override
	public int getColumnCount() {
		return 10;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (data == null) {
			return null;
		}
		TBMessageBigAttributesTable tbMessageBigAttributes = (TBMessageBigAttributesTable) data.get(rowIndex);
		switch (columnIndex) {
			case 0: return tbMessageBigAttributes.getId();
			case 1: return tbMessageBigAttributes.getTbMsgId();
			case 2: return tbMessageBigAttributes.getDocGuid();
			case 3: return tbMessageBigAttributes.getAttGuid();
			case 4: return tbMessageBigAttributes.getDescription();
			case 5: return tbMessageBigAttributes.getOrdinalNumber();
			case 6: return tbMessageBigAttributes.getName();
			case 7: return tbMessageBigAttributes.getType();
			case 8: return tbMessageBigAttributes.getContentType();
			case 9: return tbMessageBigAttributes.getPreparedDate();

			default: return null;
		}
	}

	@Override
	public String getColumnName(int columnIdx) {
		return COLUMN_NAMES[columnIdx];
	}

	@Override
	public void setData(List<PageableData> data) {
		this.data = data;
	}

	@Override
	public void updateTable() {
		super.fireTableDataChanged();
	}
}
