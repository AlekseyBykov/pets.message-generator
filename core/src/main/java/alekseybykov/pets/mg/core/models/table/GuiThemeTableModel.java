package alekseybykov.pets.mg.core.models.table;

import alekseybykov.pets.mg.core.businessobjects.PageableData;
import alekseybykov.pets.mg.core.businessobjects.pageable.GuiTheme;
import org.springframework.stereotype.Component;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

@Component
public class GuiThemeTableModel extends AbstractTableModel implements TableModel {

	private static final String[] COLUMN_NAMES = {
			"Тема", "active"
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
		return (columnIndex == 0) ? String.class : Boolean.class;
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

		GuiTheme guiTheme = (GuiTheme) data.get(rowIndex);
		switch (columnIndex) {
			case 0: return guiTheme.getName();
			case 1: return guiTheme.isActive();

			default: return null;
		}
	}

	public void updateModel(
			GuiTheme updatedGuiTheme,
			int rowIndex
	) {
		GuiTheme selectedGuiTheme = ((GuiTheme) data.get(rowIndex));
		selectedGuiTheme.setActive(updatedGuiTheme.isActive());
		setValueAt(selectedGuiTheme, rowIndex, 1);

		for (int i = 0; i < getRowCount(); i++) {
			if (rowIndex != i) {
				GuiTheme deselectedGuiTheme = ((GuiTheme) data.get(i));
				deselectedGuiTheme.setActive(false);
				setValueAt(deselectedGuiTheme, i, 1);
			}
		}

		fireTableDataChanged();
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
