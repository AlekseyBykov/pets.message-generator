package alekseybykov.pets.mg.core.models.table;

import alekseybykov.pets.mg.core.businessobjects.PageableData;
import alekseybykov.pets.mg.core.businessobjects.pageable.SqlLogRow;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import javax.swing.table.AbstractTableModel;
import java.util.List;

@Component
public class SqlLogTableModel extends AbstractTableModel implements TableModel {

	private static final String[] COLUMN_NAMES = {
			"идентификатор запроса",
			"когда выполнился впервые",
			"кем выполнен",
			"в какой схеме выполнен",
			"текст выполненного запроса",
			"сколько раз выполнился всего"
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
		return 6;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (data == null) {
			return null;
		}

		SqlLogRow sqlLogRow = (SqlLogRow) data.get(rowIndex);
		switch (columnIndex) {
			case 0: return sqlLogRow.getSqlId();
			case 1: return sqlLogRow.getFirstLoadTime();
			case 2: return sqlLogRow.getUserName();
			case 3: return sqlLogRow.getParsingSchemaName();
			case 4: return sqlLogRow.getSqlText();
			case 5: return sqlLogRow.getRowsProcessed();

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
