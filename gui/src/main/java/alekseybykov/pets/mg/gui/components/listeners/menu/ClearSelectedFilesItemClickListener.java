package alekseybykov.pets.mg.gui.components.listeners.menu;

import alekseybykov.pets.mg.core.models.table.AttachmentFileListTableModel;
import alekseybykov.pets.mg.core.models.table.OAGISFileListTableModel;
import alekseybykov.pets.mg.core.models.table.TFFFileListTableModel;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
public class ClearSelectedFilesItemClickListener implements ActionListener {

	private final static Object[] MODAL_DIALOG_OPTIONS = { "Да", "Нет" };

	@Autowired
	private OAGISFileListTableModel oagisFileListTableModel;
	@Autowired
	private AttachmentFileListTableModel attachmentFileListTableModel;
	@Autowired
	private TFFFileListTableModel tffFileListTableModel;

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		int selectedOption = JOptionPane.showOptionDialog(
				null,
				"Вы действительно хотите сбросить текущий набор?",
				"Подтвердите выбор",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				MODAL_DIALOG_OPTIONS,
				MODAL_DIALOG_OPTIONS[0]
		);
		if (selectedOption == NumberUtils.INTEGER_ZERO) {
			clearModels();
		}
	}

	private void clearModels() {
		oagisFileListTableModel.clear();
		attachmentFileListTableModel.clear();
		tffFileListTableModel.clear();
	}
}
