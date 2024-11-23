package alekseybykov.pets.mg.gui.components.listeners.menu;

import alekseybykov.pets.mg.core.models.table.TFFFileListTableModel;
import alekseybykov.pets.mg.gui.components.tables.tff.TFFFileListTable;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
public class RemoveTFFItemClickListener implements ActionListener {

	@Autowired
	private TFFFileListTable table;
	@Autowired
	private TFFFileListTableModel tffFileListTableModel;

	public void actionPerformed(ActionEvent actionEvent) {
		int selectedRow = table.getSelectedRow();
		if (selectedRow != NumberUtils.INTEGER_MINUS_ONE) {
			tffFileListTableModel.removeSelectedRow(selectedRow);
		}
	}
}
