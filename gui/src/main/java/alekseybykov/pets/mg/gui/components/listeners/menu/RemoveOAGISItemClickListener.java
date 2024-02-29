package alekseybykov.pets.mg.gui.components.listeners.menu;

import alekseybykov.pets.mg.core.models.table.OAGISFileListTableModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author bykov.alexey
 * @since 19.02.2024
 */
@Component
public class RemoveOAGISItemClickListener implements ActionListener {

	@Autowired
	private OAGISFileListTableModel oagisFileListTableModel;

	public void actionPerformed(ActionEvent actionEvent) {
		oagisFileListTableModel.clear();
	}
}
