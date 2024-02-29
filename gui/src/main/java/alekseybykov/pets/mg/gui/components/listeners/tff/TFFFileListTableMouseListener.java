package alekseybykov.pets.mg.gui.components.listeners.tff;

import alekseybykov.pets.mg.gui.components.menus.popup.TFFTablePopupMenu;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author bykov.alexey
 * @since 16.02.2024
 */
@Component
public class TFFFileListTableMouseListener extends MouseAdapter {

	@Autowired
	private TFFTablePopupMenu popupMenu;

	@Override
	public void mouseReleased(MouseEvent mouseEvent) {
		JTable table = (JTable) mouseEvent.getSource();
		val selectedRow = table.rowAtPoint(mouseEvent.getPoint());
		if (selectedRow >= 0 && selectedRow < table.getRowCount()) {
			table.setRowSelectionInterval(selectedRow, selectedRow);
		} else {
			table.clearSelection();
		}

		val rowIndex = table.getSelectedRow();
		if (rowIndex < 0) {
			return;
		}

		if (mouseEvent.isPopupTrigger() && mouseEvent.getComponent() instanceof JTable) {
			popupMenu.show(mouseEvent.getComponent(), mouseEvent.getX(), mouseEvent.getY());
		}
	}
}
