package alekseybykov.pets.mg.core.models.table;

import alekseybykov.pets.mg.core.businessobjects.PageableData;
import alekseybykov.pets.mg.core.businessobjects.pageable.OutcomeQueueTable;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * @author bykov.alexey
 * @since 18.04.2021
 */
@Component
public class OutcomeQueueTableModel extends AbstractTableModel implements TableModel {

	private static final String[] COLUMN_NAMES = {
			"id", "url", "createdate", "seqnum", "sequencesize",
			"guid", "seqguid", "file_size", "contentclassname",
			"status", "errormessage", "errorcode", "to_complex_id",
			"change_status_date", "priority", "owner_docs_count",
			"processcount", "info"
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
		return 18;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (data == null) {
			return null;
		}

		OutcomeQueueTable outcomeQueue = (OutcomeQueueTable) data.get(rowIndex);
		switch (columnIndex) {
			case 0: return outcomeQueue.getId();
			case 1: return outcomeQueue.getUrl();
			case 2: return outcomeQueue.getCreateDate();
			case 3: return outcomeQueue.getSeqNum();
			case 4: return outcomeQueue.getSequenceSize();
			case 5: return outcomeQueue.getGuid();
			case 6: return outcomeQueue.getSeqGuid();
			case 7: return outcomeQueue.getFileSize();
			case 8: return outcomeQueue.getContentClassname();
			case 9: return outcomeQueue.getStatus();
			case 10: return outcomeQueue.getErrorMessage();
			case 11: return outcomeQueue.getErrorCode();
			case 12: return outcomeQueue.getToComplexId();
			case 13: return outcomeQueue.getChangeStatusDate();
			case 14: return outcomeQueue.getPriority();
			case 15: return outcomeQueue.getOwnerDocsCount();
			case 16: return outcomeQueue.getProcessCount();
			case 17: return outcomeQueue.getInfo();

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
