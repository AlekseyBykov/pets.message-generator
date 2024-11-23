package alekseybykov.pets.mg.core.models.table;

import alekseybykov.pets.mg.core.businessobjects.file.OAGISFile;
import lombok.Getter;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import javax.swing.table.AbstractTableModel;

@Component
public class OAGISFileListTableModel extends AbstractTableModel {

	private static final String[] COLUMN_NAMES = {
			"Имя файла OAGIS (редактируемое поле)",
			"Путь к файлу",
			"Размер файла (байт)"
	};

	@Getter
	private OAGISFile oagisFile;

	@Override
	public int getRowCount() {
		return oagisFile == null
		       ? NumberUtils.INTEGER_ZERO
		       : NumberUtils.INTEGER_ONE;
	}

	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return COLUMN_NAMES[columnIndex];
	}

	public void setOAGISFile(OAGISFile oagisFile) {
		this.oagisFile = oagisFile;
		fireTableDataChanged();
	}

	@Override
	public void setValueAt(Object value, int row, int column) {
		if (column == 0) {
			oagisFile.setName((String) value);
		}
		fireTableDataChanged();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (oagisFile == null) {
			return null;
		}

		switch (columnIndex) {
			case 0: return oagisFile.getName();
			case 1: return oagisFile.getPath();
			case 2: return oagisFile.getSize();

			default: return null;
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex == 0;
	}

	public void clear() {
		oagisFile = null;
		fireTableDataChanged();
	}
}
