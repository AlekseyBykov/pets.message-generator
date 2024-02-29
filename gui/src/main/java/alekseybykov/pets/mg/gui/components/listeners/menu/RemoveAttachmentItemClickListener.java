package alekseybykov.pets.mg.gui.components.listeners.menu;

import alekseybykov.pets.mg.core.models.table.AttachmentFileListTableModel;
import alekseybykov.pets.mg.gui.components.tables.attachments.AttachmentFileListTable;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author bykov.alexey
 * @since 29.04.2022
 */
@Component
public class RemoveAttachmentItemClickListener implements ActionListener {

	@Autowired
	private AttachmentFileListTable attachmentFileListTable;
	@Autowired
	private AttachmentFileListTableModel attachmentFileListTableModel;

	public void actionPerformed(ActionEvent actionEvent) {
		int selectedRow = attachmentFileListTable.getSelectedRow();
		if (selectedRow != NumberUtils.INTEGER_MINUS_ONE) {
			attachmentFileListTableModel.removeSelectedRow(selectedRow);
		}
	}
}
