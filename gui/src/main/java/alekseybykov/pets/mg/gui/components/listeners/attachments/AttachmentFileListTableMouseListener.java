package alekseybykov.pets.mg.gui.components.listeners.attachments;

import alekseybykov.pets.mg.gui.components.menus.popup.AttachmentsTablePopupMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author bykov.alexey
 * @since 16.03.2021
 */
@Component
public class AttachmentFileListTableMouseListener extends MouseAdapter {

	@Autowired
	private AttachmentsTablePopupMenu popupMenu;

	@Override
	public void mouseReleased(MouseEvent mouseEvent) {
		JTable table = (JTable) mouseEvent.getSource();
		int selectedRow = table.rowAtPoint(mouseEvent.getPoint());
		if (selectedRow >= 0 && selectedRow < table.getRowCount()) {
			table.setRowSelectionInterval(selectedRow, selectedRow);
		} else {
			table.clearSelection();
		}

		int rowIndex = table.getSelectedRow();
		if (rowIndex < 0) {
			return;
		}

		if (mouseEvent.isPopupTrigger() && mouseEvent.getComponent() instanceof JTable) {
			popupMenu.show(mouseEvent.getComponent(), mouseEvent.getX(), mouseEvent.getY());
		}
	}
}
