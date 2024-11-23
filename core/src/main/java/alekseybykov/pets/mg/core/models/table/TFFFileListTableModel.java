package alekseybykov.pets.mg.core.models.table;

import alekseybykov.pets.mg.core.businessobjects.file.TFFFile;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

@Component
public class TFFFileListTableModel extends AbstractTableModel {

	private static final String[] COLUMN_NAMES = {
			"Имя файла ТФФ (редактируемое поле)",
			"Путь к файлу",
			"Размер файла (байт)"
	};

	@Setter @Getter
	private List<TFFFile> tffFiles = new ArrayList<>();

	@Override
	public int getRowCount() {
		if (tffFiles == null) {
			return NumberUtils.INTEGER_ZERO;
		}
		return tffFiles.size();
	}

	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return COLUMN_NAMES[columnIndex];
	}

	public void addTFF(TFFFile tffFile) {
		tffFiles.add(tffFile);
		fireTableDataChanged();
	}

	@Override
	public void setValueAt(
            Object value,
            int row,
            int column
    ) {
		if (column == 0) {
			tffFiles.get(row).setName((String) value);
		}
		fireTableDataChanged();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (tffFiles == null) {
			return null;
		}
		TFFFile tffFile = tffFiles.get(rowIndex);
		switch (columnIndex) {
			case 0: return tffFile.getName();
			case 1: return tffFile.getPath();
			case 2: return tffFile.getSize();

			default: return null;
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex == 0;
	}

	public void removeSelectedRow(int rowIndex) {
		tffFiles.remove(rowIndex);
		fireTableDataChanged();
	}

	public void clear() {
		tffFiles.clear();
		fireTableDataChanged();
	}
}
