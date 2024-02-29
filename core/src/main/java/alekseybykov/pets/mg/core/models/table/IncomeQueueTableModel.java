package alekseybykov.pets.mg.core.models.table;


import alekseybykov.pets.mg.core.businessobjects.PageableData;
import alekseybykov.pets.mg.core.businessobjects.pageable.IncomeQueueTable;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * @author bykov.alexey
 * @since 16.04.2021
 */
@Component
public class IncomeQueueTableModel extends AbstractTableModel implements TableModel {

	private static final String[] COLUMN_NAMES = {
			"id", "filename", "receivedate", "seqguid", "file_size",
			"errormessage", "status", "complextype", "exported",
			"errorcode", "change_status_date", "priority",
			"processcount", "owner_docs_count", "obtained_through",
			"info", "recipient", "sender"
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

		IncomeQueueTable incomeQueue = (IncomeQueueTable) data.get(rowIndex);
		switch (columnIndex) {
			case 0: return incomeQueue.getId();
			case 1: return incomeQueue.getFileName();
			case 2: return incomeQueue.getReceiveDate();
			case 3: return incomeQueue.getSeqGuid();
			case 4: return incomeQueue.getFileSize();
			case 5: return incomeQueue.getErrorMessage();
			case 6: return incomeQueue.getStatus();
			case 7: return incomeQueue.getComplexType();
			case 8: return incomeQueue.getExported();
			case 9: return incomeQueue.getErrorCode();
			case 10: return incomeQueue.getChangeStatusDate();
			case 11: return incomeQueue.getPriority();
			case 12: return incomeQueue.getProcessCount();
			case 13: return incomeQueue.getOwnerDocsCount();
			case 14: return incomeQueue.getObtainedThrough();
			case 15: return incomeQueue.getInfo();
			case 16: return incomeQueue.getRecipient();
			case 17: return incomeQueue.getSender();

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
