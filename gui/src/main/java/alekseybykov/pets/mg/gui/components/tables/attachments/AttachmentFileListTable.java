package alekseybykov.pets.mg.gui.components.tables.attachments;

import alekseybykov.pets.mg.core.models.table.AttachmentFileListTableModel;
import alekseybykov.pets.mg.gui.components.listeners.attachments.AttachmentFileListTableMouseListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;

/**
 * @author bykov.alexey
 * @since 27.02.2024
 */
@Component
public class AttachmentFileListTable extends JTable {

	@Autowired
	private AttachmentFileListTableModel attachmentFileListTableModel;
	@Autowired
	private AttachmentFileListTableMouseListener attachmentFileListTableMouseListener;

	@PostConstruct
	private void postConstruct() {
		setModel(attachmentFileListTableModel);
		getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		addMouseListener(attachmentFileListTableMouseListener);
	}
}
