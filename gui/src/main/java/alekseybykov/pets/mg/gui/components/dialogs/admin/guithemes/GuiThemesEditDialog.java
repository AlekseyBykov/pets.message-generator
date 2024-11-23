package alekseybykov.pets.mg.gui.components.dialogs.admin.guithemes;

import alekseybykov.pets.mg.core.businessobjects.pageable.GuiTheme;
import alekseybykov.pets.mg.core.exceptions.RowIndexOutOfBoundsException;
import alekseybykov.pets.mg.core.models.table.GuiThemeTableModel;
import alekseybykov.pets.mg.gui.components.helpers.GUIHelper;
import alekseybykov.pets.mg.gui.components.panels.common.GuiThemeDetailsPanel;
import alekseybykov.pets.mg.gui.components.tables.guithemes.GuiThemesTable;
import alekseybykov.pets.mg.gui.uiconsts.buttons.ButtonTitles;
import alekseybykov.pets.mg.gui.uiconsts.localresources.IconPaths;
import alekseybykov.pets.mg.services.guitheme.GuiThemeService;
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

@Component
public class GuiThemesEditDialog extends JDialog {

	@Getter
	@Autowired
	private GuiThemeDetailsPanel detailsPanel;
	@Getter
	@Autowired
	private GuiThemesTable table;

	@Autowired
	private GuiThemeTableModel model;

	@Autowired
	private GuiThemeService service;

	public void populateDialogFromTableRow() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow == NumberUtils.INTEGER_MINUS_ONE) {
			JOptionPane.showMessageDialog(new JFrame(),
					"Тема не выбрана.\nСледует выбрать тему для редактирования.",
					"Ошибка редактирования темы",
					JOptionPane.ERROR_MESSAGE);
			throw new RowIndexOutOfBoundsException("Тема не выбрана.\nСледует выбрать тему для редактирования.");
		}

		detailsPanel.getNameTextField().setText((String) model.getValueAt(selectedRow, 0));
		detailsPanel.getActiveSign().setSelected((Boolean) model.getValueAt(selectedRow, 1));
	}

	@PostConstruct
	private void postConstruct() {
		setTitle("Выбор активной темы");
		setSize(500, 300);

		setModal(true);
		setResizable(false);
		buildLayout();
	}

	private void buildLayout() {
		setLayout(new BorderLayout());

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		panel.add(detailsPanel);
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
				IconPaths.OK_BTN_ICON.getPath(), new OkButtonListener(this, service, table));
	}

	private JButton buildCancelButton() {
		return GUIHelper.buildButton(20, 20, ButtonTitles.CANCEL_BTN.getTitle(), StringUtils.EMPTY,
				IconPaths.CANCEL_BTN_ICON.getPath(), new CancelButtonListener(this));
	}

	@AllArgsConstructor
	private class OkButtonListener implements ActionListener {

		private final GuiThemesEditDialog dialog;
		private final GuiThemeService service;
		private final GuiThemesTable table;

		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			GuiThemeDetailsPanel guiThemeDetailsPanel = dialog.getDetailsPanel();

			val name = guiThemeDetailsPanel.getNameTextField().getText();
			val active = guiThemeDetailsPanel.getActiveSign().isSelected();

			GuiTheme guiTheme = GuiTheme.builder()
					.name(name)
					.active(active)
					.build();

			SwingWorker<Boolean, Void> guiThemesUpdater = new SwingWorker<Boolean, Void>() {

				@Override
				@SneakyThrows
				protected Boolean doInBackground() {
					service.deselectAnotherTheme(guiTheme);
					service.selectTheme(guiTheme);
					return true;
				}

				@Override
				@SneakyThrows
				protected void done() {
					try {
						if (get()) {
							model.updateModel(guiTheme, table.getSelectedRow());
						}
					} finally {
						dialog.setVisible(false);
						dialog.dispose();
					}
				}
			};
			guiThemesUpdater.execute();
		}
	}

	@AllArgsConstructor
	private static class CancelButtonListener implements ActionListener {

		private final JDialog dialog;

		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			dialog.setVisible(false);
			dialog.dispose();
		}
	}
}
