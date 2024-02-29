package alekseybykov.pets.mg.gui.components.dialogs.admin.stands;

import alekseybykov.pets.mg.core.businessobjects.pageable.OracleConnection;
import alekseybykov.pets.mg.core.coreconsts.ErrorMessages;
import alekseybykov.pets.mg.core.exceptions.RowIndexOutOfBoundsException;
import alekseybykov.pets.mg.core.models.table.DBConnectionConfigsTableModel;
import alekseybykov.pets.mg.core.utils.strings.MGStringUtils;
import alekseybykov.pets.mg.gui.components.helpers.GUIHelper;
import alekseybykov.pets.mg.gui.components.panels.common.DBConnectionConfigDetailsPanel;
import alekseybykov.pets.mg.gui.components.tables.admin.DBConnectionConfigsTable;
import alekseybykov.pets.mg.gui.uiconsts.buttons.ButtonTitles;
import alekseybykov.pets.mg.gui.uiconsts.dialogs.stands.StandsConfigDialogSizes;
import alekseybykov.pets.mg.gui.uiconsts.dialogs.stands.StandsConfigDialogTitles;
import alekseybykov.pets.mg.gui.uiconsts.localresources.IconPaths;
import alekseybykov.pets.mg.services.dbconnection.DBConnectionService;
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
 * @since 27.06.2021
 */
@Component
public class EditStandsConfigDialog extends JDialog {

	@Getter
	@Autowired
	private DBConnectionConfigDetailsPanel configDetailsPanel;

	@Getter
	@Autowired
	private DBConnectionConfigsTable table;

	@Autowired
	private DBConnectionService dbConnectionService;

	@PostConstruct
	private void postConstruct() {
		setTitle(StandsConfigDialogTitles.EDIT_CONFIG_DIALOG_TITLE.getName());

		setSize(StandsConfigDialogSizes.EDIT_CONFIG_DIALOG_WINDOW_SIZE_WIDTH.getValue(),
				StandsConfigDialogSizes.EDIT_CONFIG_DIALOG_WINDOW_SIZE_HEIGHT.getValue());

		setModal(true);
		setResizable(false);
		buildLayout();
	}

	private void buildLayout() {
		setLayout(new BorderLayout());

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		panel.add(configDetailsPanel);
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
				IconPaths.OK_BTN_ICON.getPath(), new OkButtonListener(this, dbConnectionService, table));
	}

	private JButton buildCancelButton() {
		return GUIHelper.buildButton(20, 20, ButtonTitles.CANCEL_BTN.getTitle(), StringUtils.EMPTY,
				IconPaths.CANCEL_BTN_ICON.getPath(), new CancelButtonListener(this));
	}

	public void populateDialogFromTableRow() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow == NumberUtils.INTEGER_MINUS_ONE) {
			JOptionPane.showMessageDialog(new JFrame(), ErrorMessages.CONFIG_NOT_SELECTED_FOR_EDIT_ERROR_TEXT.getName(),
					ErrorMessages.EDIT_CONFIG_ERROR.getName(),
					JOptionPane.ERROR_MESSAGE);
			clearTextFields();
			throw new RowIndexOutOfBoundsException(ErrorMessages.CONFIG_NOT_SELECTED_FOR_EDIT_ERROR_TEXT.getName());
		}

		DBConnectionConfigsTableModel tableModel = (DBConnectionConfigsTableModel) table.getModel();
		configDetailsPanel.getActiveSign().setSelected((Boolean) tableModel.getValueAt(selectedRow, 0));
		configDetailsPanel.getNameTextField().setText((String) tableModel.getValueAt(selectedRow, 1));
		configDetailsPanel.getUrlTextField().setText((String) tableModel.getValueAt(selectedRow, 2));
		configDetailsPanel.getDriverClassNameTextField().setText((String) tableModel.getValueAt(selectedRow, 3));
		configDetailsPanel.getUserTextField().setText((String) tableModel.getValueAt(selectedRow, 4));
		configDetailsPanel.getPasswordTextField().setText((String) tableModel.getValueAt(selectedRow, 5));
	}

	@AllArgsConstructor
	private class OkButtonListener implements ActionListener {

		private final EditStandsConfigDialog dialog;
		private final DBConnectionService dbConnectionService;
		private final DBConnectionConfigsTable table;

		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			DBConnectionConfigDetailsPanel configDetailsPanel = dialog.getConfigDetailsPanel();

			val configName = configDetailsPanel.getNameTextField().getText();
			val user = configDetailsPanel.getUserTextField().getText();
			val password = configDetailsPanel.getPasswordTextField().getText();
			val driverClassName = configDetailsPanel.getDriverClassNameTextField().getText();
			val url = configDetailsPanel.getUrlTextField().getText();

			boolean active = Boolean.FALSE;
			if (configDetailsPanel.getActiveSign().isSelected()) {
				active = true;
			}

			if (StringUtils.isEmpty(user) || StringUtils.isEmpty(password)
			    || StringUtils.isEmpty(driverClassName) || StringUtils.isEmpty(url)) {
				JOptionPane.showMessageDialog(new JFrame(), ErrorMessages.CONFIG_EMPTY_FIELDS_ERROR_TEXT.getName(),
						ErrorMessages.EDIT_CONFIG_ERROR.getName(),
						JOptionPane.ERROR_MESSAGE);
				clearTextFields();
				return;
			}

			if (MGStringUtils.matchCyrillic(configName)) {
				JOptionPane.showMessageDialog(new JFrame(), ErrorMessages.CONFIG_ILLEGAL_NAME_ERROR_TEXT.getName(),
						ErrorMessages.EDIT_CONFIG_ERROR.getName(),
						JOptionPane.ERROR_MESSAGE);
				clearTextFields();
				return;
			}

			final OracleConnection newOracleConnection = OracleConnection.builder()
					.active(active)
					.name(configName)
					.user(configDetailsPanel.getUserTextField().getText())
					.password(configDetailsPanel.getPasswordTextField().getText())
					.driverClassName(configDetailsPanel.getDriverClassNameTextField().getText())
					.url(configDetailsPanel.getUrlTextField().getText())
					.build();

			val oldOracleConnection = getOldConnectionName(table);

			SwingWorker<Boolean, Void> configSaver = new SwingWorker<Boolean, Void>() {
				@Override
				@SneakyThrows
				protected Boolean doInBackground() {
					if (!dbConnectionService.isConfigDuplicated(newOracleConnection)) {
						dbConnectionService.updateConfig(newOracleConnection, oldOracleConnection);
						return true;
					}
					return false;
				}

				@Override
				@SneakyThrows
				protected void done() {
					try {
						if (get()) {
							table.getTableModel().updateConfig(newOracleConnection, table.getSelectedRow());
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

	private String getOldConnectionName(DBConnectionConfigsTable table) {
		return table.getModel().getValueAt(table.getSelectedRow(), 1).toString();
	}

	private void clearTextFields() {
		configDetailsPanel.getNameTextField().setText(StringUtils.EMPTY);
		configDetailsPanel.getDriverClassNameTextField().setText(StringUtils.EMPTY);
		configDetailsPanel.getUrlTextField().setText(StringUtils.EMPTY);
		configDetailsPanel.getUserTextField().setText(StringUtils.EMPTY);
		configDetailsPanel.getPasswordTextField().setText(StringUtils.EMPTY);
	}
}
