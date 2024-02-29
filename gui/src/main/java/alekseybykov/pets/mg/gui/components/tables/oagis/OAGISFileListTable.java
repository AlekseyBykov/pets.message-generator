package alekseybykov.pets.mg.gui.components.tables.oagis;

import alekseybykov.pets.mg.core.models.table.OAGISFileListTableModel;
import alekseybykov.pets.mg.gui.components.listeners.oagis.OAGISFileListTableMouseListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;

/**
 * @author bykov.alexey
 * @since 27.02.2024
 */
@Component
public class OAGISFileListTable extends JTable {

	@Autowired
	private OAGISFileListTableModel oagisFileListTableModel;
	@Autowired
	private OAGISFileListTableMouseListener oagisFileListTableMouseListener;

	@PostConstruct
	private void postConstruct() {
		setModel(oagisFileListTableModel);
		getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		addMouseListener(oagisFileListTableMouseListener);
	}
}
