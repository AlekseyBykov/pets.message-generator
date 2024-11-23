package alekseybykov.pets.mg.core.models.table;

import alekseybykov.pets.mg.core.businessobjects.PageableData;
import alekseybykov.pets.mg.core.businessobjects.pageable.LocalPath;
import org.springframework.stereotype.Component;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

@Component
public class LocalPathsTableModel
		extends AbstractTableModel implements TableModel {

	private static final String[] COLUMN_NAMES = {
		"идентификатор", "абсолютный путь"
	};

	private List<PageableData> data = new ArrayList<>();

	@Override
	public String getColumnName(int columnIdx) {
		return COLUMN_NAMES[columnIdx];
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (data == null) {
			return null;
		}

		LocalPath localPath = (LocalPath) data.get(rowIndex);
		switch (columnIndex) {
			case 0: return localPath.getName();
			case 1: return localPath.getPath();

			default: return null;
		}
	}

	@Override
	public void setData(List<PageableData> data) {
		this.data = data;
	}

	public void updateModel(LocalPath updatedLocalPath, int rowIndex) {
		LocalPath currentLocalPath = ((LocalPath) data.get(rowIndex));
		currentLocalPath.setPath(updatedLocalPath.getPath());

		fireTableDataChanged();
	}

	@Override
	public void updateTable() {
		super.fireTableDataChanged();
	}
}
