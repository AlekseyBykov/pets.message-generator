package alekseybykov.pets.mg.gui.components.dialogs.admin.localpath;

import alekseybykov.pets.mg.gui.components.helpers.GUIHelper;
import alekseybykov.pets.mg.gui.components.listeners.admin.localpaths.EditLocalPathButtonClickListener;
import alekseybykov.pets.mg.gui.components.listeners.common.CloseDialogButtonListener;
import alekseybykov.pets.mg.gui.components.panels.admin.localpaths.LocalPathsTablePanel;
import alekseybykov.pets.mg.gui.uiconsts.buttons.ButtonTitles;
import alekseybykov.pets.mg.gui.uiconsts.dialogs.localpath.LocalPathDialogSizes;
import alekseybykov.pets.mg.gui.uiconsts.dialogs.localpath.LocalPathDialogTitles;
import alekseybykov.pets.mg.gui.uiconsts.localresources.IconPaths;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

@Component
public class LocalPathsDialog extends JDialog {

	@Autowired
	private LocalPathsTablePanel localPathsTablePanel;
	@Autowired
	private EditLocalPathButtonClickListener editClickListener;

	@PostConstruct
	private void postConstruct() {
		setTitle(LocalPathDialogTitles.COMMON_DIALOG_TITLE.getName());
		setSize(LocalPathDialogSizes.COMMON_DIALOG_WIDTH.getValue(),
				LocalPathDialogSizes.COMMON_DIALOG_HEIGHT.getValue());

		setModal(true);
		setResizable(false);
		buildLayout();
	}

	private void buildLayout() {
		setLayout(new BorderLayout());

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(localPathsTablePanel);
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
		return GUIHelper.buildButton(20, 20, LocalPathDialogTitles.EDIT_LOCAL_PATH_BTN.getName(), StringUtils.EMPTY,
				IconPaths.EDIT_BTN_ICON.getPath(), editClickListener);
	}

	private JButton buildCloseDialogButton() {
		return GUIHelper.buildButton(0, 0, ButtonTitles.CLOSE_BTN.getTitle(), StringUtils.EMPTY,
				StringUtils.EMPTY, new CloseDialogButtonListener(this));
	}
}
