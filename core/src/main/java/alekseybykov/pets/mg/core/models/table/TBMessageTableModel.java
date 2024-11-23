package alekseybykov.pets.mg.core.models.table;

import alekseybykov.pets.mg.core.businessobjects.PageableData;
import alekseybykov.pets.mg.core.businessobjects.pageable.TBMessageTable;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import javax.swing.table.AbstractTableModel;
import java.util.List;

@Component
public class TBMessageTableModel extends AbstractTableModel implements TableModel {

	private static final String[] COLUMN_NAMES = {
			"id", "route", "sys_from_code", "sys_to_code", "priority", "queue_name",
			"desc_message", "time_create", "time_accept", "time_finish",
			"status", "error_code", "error_text", "time_last_processed",
			"processed_count"
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
		return 15;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (data == null) {
			return null;
		}
		TBMessageTable tbMessage = (TBMessageTable) data.get(rowIndex);
		switch (columnIndex) {
			case 0: return tbMessage.getId();
			case 1: return tbMessage.getRouteMarker();
			case 2: return tbMessage.getSysFromCode();
			case 3: return tbMessage.getSysToCode();
			case 4: return tbMessage.getPriority();
			case 5: return tbMessage.getQueueName();
			case 6: return tbMessage.getDescription();
			case 7: return tbMessage.getTimeCreate();
			case 8: return tbMessage.getTimeAccept();
			case 9: return tbMessage.getTimeFinish();
			case 10: return tbMessage.getStatus();
			case 11: return tbMessage.getErrorCode();
			case 12: return tbMessage.getErrorText();
			case 13: return tbMessage.getTimeLastProcessed();
			case 14: return tbMessage.getProcessedCount();

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
