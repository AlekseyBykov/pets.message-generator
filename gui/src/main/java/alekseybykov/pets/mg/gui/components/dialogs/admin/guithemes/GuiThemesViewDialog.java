package alekseybykov.pets.mg.gui.components.dialogs.admin.guithemes;

import alekseybykov.pets.mg.gui.components.helpers.GUIHelper;
import alekseybykov.pets.mg.gui.components.listeners.admin.guithemes.GuiThemesEditClickListener;
import alekseybykov.pets.mg.gui.components.listeners.common.CloseDialogButtonListener;
import alekseybykov.pets.mg.gui.components.panels.admin.guithemes.GuiThemeTablePanel;
import alekseybykov.pets.mg.gui.uiconsts.buttons.ButtonTitles;
import alekseybykov.pets.mg.gui.uiconsts.localresources.IconPaths;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * @author bykov.alexey
 * @since 25.02.2024
 */
@Component
public class GuiThemesViewDialog extends JDialog {

	@Autowired
	private GuiThemeTablePanel guiThemeTablePanel;
	@Autowired
	private GuiThemesEditClickListener clickListener;

	@PostConstruct
	private void postConstruct() {
		setTitle("Темы интерфейса (перезапустить после выбора)");
		setSize(700, 450);

		setModal(true);
		setResizable(false);
		buildLayout();
	}

	private void buildLayout() {
		setLayout(new BorderLayout());

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(guiThemeTablePanel);
		panel.add(buildButtonsPanel());

		add(panel, BorderLayout.CENTER);
	}

	private JPanel buildButtonsPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		JPanel systemButtonsPanel = new JPanel();
		systemButtonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		systemButtonsPanel.setBorder(new EmptyBorder(0, 0, 10, 10));
		systemButtonsPanel.add(buildEditLocalPathButton());
		systemButtonsPanel.add(buildCloseDialogButton());

		panel.add(systemButtonsPanel);

		return panel;
	}

	private JButton buildEditLocalPathButton() {
		return GUIHelper.buildButton(20, 20, "Редактировать", StringUtils.EMPTY,
				IconPaths.EDIT_BTN_ICON.getPath(), clickListener);
	}

	private JButton buildCloseDialogButton() {
		return GUIHelper.buildButton(0, 0, ButtonTitles.CLOSE_BTN.getTitle(), StringUtils.EMPTY,
				StringUtils.EMPTY, new CloseDialogButtonListener(this));
	}
}
