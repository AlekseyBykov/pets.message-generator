package alekseybykov.pets.mg.gui.components.dialogs.admin.stands;

import alekseybykov.pets.mg.gui.components.helpers.GUIHelper;
import alekseybykov.pets.mg.gui.components.listeners.admin.stands.AddNewConfigButtonClickListener;
import alekseybykov.pets.mg.gui.components.listeners.admin.stands.EditConfigButtonClickListener;
import alekseybykov.pets.mg.gui.components.listeners.admin.stands.RemoveConfigButtonClickListener;
import alekseybykov.pets.mg.gui.components.listeners.common.CloseDialogButtonListener;
import alekseybykov.pets.mg.gui.components.panels.admin.db.DBConnectionConfigsTablePanel;
import alekseybykov.pets.mg.gui.uiconsts.buttons.ButtonTitles;
import alekseybykov.pets.mg.gui.uiconsts.dialogs.stands.StandsConfigDialogSizes;
import alekseybykov.pets.mg.gui.uiconsts.dialogs.stands.StandsConfigDialogTitles;
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
 * @since 25.06.2021
 */
@Component
public class StandsConfigDialog extends JDialog {

	@Autowired
	private DBConnectionConfigsTablePanel dbConnectionConfigsTablePanel;
	@Autowired
	private AddNewConfigButtonClickListener addNewConfigButtonClickListener;
	@Autowired
	private EditConfigButtonClickListener editConfigButtonClickListener;
	@Autowired
	private RemoveConfigButtonClickListener removeConfigButtonClickListener;

	@PostConstruct
	private void postConstruct() {
		setTitle(StandsConfigDialogTitles.TITLE.getName());

		setSize(StandsConfigDialogSizes.CURRENT_CONFIG_DIALOG_WIDTH.getValue(),
				StandsConfigDialogSizes.CURRENT_CONFIG_DIALOG_HEIGHT.getValue());

		setModal(true);
		setResizable(false);
		buildLayout();
	}

	private void buildLayout() {
		setLayout(new BorderLayout());

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(dbConnectionConfigsTablePanel);
		panel.add(buildButtonsPanel());

		add(panel, BorderLayout.CENTER);
	}

	private JPanel buildButtonsPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		JPanel crudButtonsPanel = new JPanel();
		crudButtonsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		crudButtonsPanel.setBorder(new EmptyBorder(0, 10, 10, 10));
		crudButtonsPanel.add(buildAddNewConfigButton());
		crudButtonsPanel.add(buildEditConfigButton());
		crudButtonsPanel.add(buildRemoveConfigButton());

		JPanel systemButtonsPanel = new JPanel();
		systemButtonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		systemButtonsPanel.setBorder(new EmptyBorder(0, 0, 10, 10));
		systemButtonsPanel.add(buildCloseDialogButton());

		panel.add(crudButtonsPanel);
		panel.add(systemButtonsPanel);

		return panel;
	}

	private JButton buildAddNewConfigButton() {
		return GUIHelper.buildButton(20, 20, StandsConfigDialogTitles.ADD_NEW_CONFIG_BTN.getName(), StringUtils.EMPTY,
				IconPaths.ADD_NEW_BTN_ICON.getPath(), addNewConfigButtonClickListener);
	}

	private JButton buildEditConfigButton() {
		return GUIHelper.buildButton(20, 20, StandsConfigDialogTitles.EDIT_CONFIG_BTN.getName(), StringUtils.EMPTY,
				IconPaths.EDIT_BTN_ICON.getPath(), editConfigButtonClickListener);
	}

	private JButton buildRemoveConfigButton() {
		return GUIHelper.buildButton(20, 20, StandsConfigDialogTitles.REMOVE_CONFIG_BTN.getName(), StringUtils.EMPTY,
				IconPaths.REMOVE_BTN_ICON.getPath(), removeConfigButtonClickListener);
	}

	private JButton buildCloseDialogButton() {
		return GUIHelper.buildButton(0, 0, ButtonTitles.CLOSE_BTN.getTitle(), StringUtils.EMPTY,
				StringUtils.EMPTY, new CloseDialogButtonListener(this));
	}
}
