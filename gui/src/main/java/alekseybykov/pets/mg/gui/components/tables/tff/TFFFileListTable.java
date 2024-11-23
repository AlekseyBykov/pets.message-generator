package alekseybykov.pets.mg.gui.components.tables.tff;

import alekseybykov.pets.mg.core.models.table.TFFFileListTableModel;
import alekseybykov.pets.mg.gui.components.listeners.tff.TFFFileListTableMouseListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;

@Component
public class TFFFileListTable extends JTable {

	@Autowired
	private TFFFileListTableModel model;
	@Autowired
	private TFFFileListTableMouseListener mouseListener;

	@PostConstruct
	private void postConstruct() {
		setModel(model);
		getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		addMouseListener(mouseListener);
	}
}
