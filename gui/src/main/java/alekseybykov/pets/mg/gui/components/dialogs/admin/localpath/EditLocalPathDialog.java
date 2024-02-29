package alekseybykov.pets.mg.gui.components.dialogs.admin.localpath;

import alekseybykov.pets.mg.core.businessobjects.pageable.LocalPath;
import alekseybykov.pets.mg.core.coreconsts.ErrorMessages;
import alekseybykov.pets.mg.core.exceptions.RowIndexOutOfBoundsException;
import alekseybykov.pets.mg.core.models.table.LocalPathsTableModel;
import alekseybykov.pets.mg.gui.components.helpers.GUIHelper;
import alekseybykov.pets.mg.gui.components.panels.common.LocalPathDetailsPanel;
import alekseybykov.pets.mg.gui.components.tables.localpaths.LocalPathsTable;
import alekseybykov.pets.mg.gui.uiconsts.buttons.ButtonTitles;
import alekseybykov.pets.mg.gui.uiconsts.dialogs.localpath.LocalPathDialogSizes;
import alekseybykov.pets.mg.gui.uiconsts.dialogs.localpath.LocalPathDialogTitles;
import alekseybykov.pets.mg.gui.uiconsts.localresources.IconPaths;
import alekseybykov.pets.mg.services.localpath.LocalPathService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author bykov.alexey
 * @since 20.02.2024
 */
@Component
public class EditLocalPathDialog extends JDialog {

	@Getter
	@Autowired
	private LocalPathDetailsPanel localPathDetailsPanel;

	@Getter
	@Autowired
	private LocalPathsTable localPathsTable;
	@Autowired
	private LocalPathsTableModel localPathsTableModel;

	@Autowired
	private LocalPathService localPathService;

	public void populateDialogFromTableRow() {
		int selectedRow = localPathsTable.getSelectedRow();
		if (selectedRow == NumberUtils.INTEGER_MINUS_ONE) {
			JOptionPane.showMessageDialog(new JFrame(), ErrorMessages.CONFIG_NOT_SELECTED_FOR_EDIT_ERROR_TEXT.getName(),
					ErrorMessages.EDIT_CONFIG_ERROR.getName(),
					JOptionPane.ERROR_MESSAGE);
			clearTextFields();
			throw new RowIndexOutOfBoundsException(ErrorMessages.CONFIG_NOT_SELECTED_FOR_EDIT_ERROR_TEXT.getName());
		}

		localPathDetailsPanel.getNameTextField().setText((String) localPathsTableModel.getValueAt(selectedRow, 0));
		localPathDetailsPanel.getPathTextField().setText((String) localPathsTableModel.getValueAt(selectedRow, 1));
	}

	@PostConstruct
	private void postConstruct() {
		setTitle(LocalPathDialogTitles.EDIT_DIALOG_TITLE.getName());
		setSize(LocalPathDialogSizes.EDIT_DIALOG_WIDTH.getValue(),
				LocalPathDialogSizes.EDIT_DIALOG_HEIGHT.getValue());

		setModal(true);
		setResizable(false);
		buildLayout();
	}

	private void buildLayout() {
		setLayout(new BorderLayout());

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		panel.add(localPathDetailsPanel);
		panel.add(buildButtonsPanel());

		add(panel, BorderLayout.CENTER);
	}

	private JPanel buildButtonsPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.setBorder(new EmptyBorder(0, 10, 10, 10));
		panel.add(buildOkButton());
		panel.add(buildCancelButton());

		return panel;
	}

	private JButton buildOkButton() {
		return GUIHelper.buildButton(20, 20, ButtonTitles.OK_BTN.getTitle(), StringUtils.EMPTY,
				IconPaths.OK_BTN_ICON.getPath(), new OkButtonListener(this, localPathService, localPathsTable));
	}

	private JButton buildCancelButton() {
		return GUIHelper.buildButton(20, 20, ButtonTitles.CANCEL_BTN.getTitle(), StringUtils.EMPTY,
				IconPaths.CANCEL_BTN_ICON.getPath(), new CancelButtonListener(this));
	}

	@AllArgsConstructor
	private class OkButtonListener implements ActionListener {

		private final EditLocalPathDialog dialog;
		private final LocalPathService service;
		private final LocalPathsTable table;

		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			LocalPathDetailsPanel localPathDetailsPanel = dialog.getLocalPathDetailsPanel();

			val name = localPathDetailsPanel.getNameTextField().getText();
			val path = localPathDetailsPanel.getPathTextField().getText();

			if (StringUtils.isEmpty(name) || StringUtils.isEmpty(path)) {
				JOptionPane.showMessageDialog(new JFrame(),
						ErrorMessages.EMPTY_FIELD_ERROR.getName(),
						ErrorMessages.EDIT_LOCAL_PATH_ERROR.getName(),
						JOptionPane.ERROR_MESSAGE);
				clearTextFields();
				return;
			}

			LocalPath updatedLocalPath = LocalPath.builder()
					.name(name)
					.path(path)
					.build();

			SwingWorker<Boolean, Void> configSaver = new SwingWorker<Boolean, Void>() {
				@Override
				@SneakyThrows
				protected Boolean doInBackground() {
					localPathService.updateLocalPath(name, path);
					return true;
				}

				@Override
				@SneakyThrows
				protected void done() {
					try {
						if (get()) {
							localPathsTableModel.updateModel(updatedLocalPath, table.getSelectedRow());
						} else {
							JOptionPane.showMessageDialog(new JFrame(), ErrorMessages.CONFIG_COLLISION_ERROR_TEXT.getName(),
									ErrorMessages.EDIT_CONFIG_ERROR.getName(),
									JOptionPane.ERROR_MESSAGE);
						}
					} finally {
						clearTextFields();
						dialog.setVisible(false);
						dialog.dispose();
					}
				}
			};
			configSaver.execute();
		}
	}

	@AllArgsConstructor
	private class CancelButtonListener implements ActionListener {

		private final JDialog dialog;

		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			clearTextFields();
			dialog.setVisible(false);
			dialog.dispose();
		}
	}

	private void clearTextFields() {
		localPathDetailsPanel.getPathTextField().setText(StringUtils.EMPTY);
	}
}
