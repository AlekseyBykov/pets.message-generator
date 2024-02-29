package alekseybykov.pets.mg.gui.components.listeners.oagis;

import alekseybykov.pets.mg.gui.components.menus.popup.OAGISTablePopupMenu;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author bykov.alexey
 * @since 19.02.2024
 */
@Component
public class OAGISFileListTableMouseListener extends MouseAdapter {

	@Autowired
	private OAGISTablePopupMenu popupMenu;

	@Override
	public void mouseReleased(MouseEvent mouseEvent) {
		JTable table = (JTable) mouseEvent.getSource();
		val selectedRow = table.rowAtPoint(mouseEvent.getPoint());
		if (selectedRow >= 0 && selectedRow < table.getRowCount()) {
			table.setRowSelectionInterval(selectedRow, selectedRow);
		} else {
			table.clearSelection();
		}

		if (mouseEvent.isPopupTrigger() && mouseEvent.getComponent() instanceof JTable) {
			popupMenu.show(mouseEvent.getComponent(), mouseEvent.getX(), mouseEvent.getY());
		}
	}
}
