package alekseybykov.pets.mg.gui.components.menus;

import alekseybykov.pets.mg.gui.components.dialogs.help.AboutDialog;
import alekseybykov.pets.mg.gui.components.dialogs.help.InformationDialog;
import alekseybykov.pets.mg.gui.components.listeners.admin.guithemes.GuiThemesItemClickListener;
import alekseybykov.pets.mg.gui.components.listeners.admin.localpaths.LocalPathsItemClickListener;
import alekseybykov.pets.mg.gui.components.listeners.admin.stands.DBConnectionConfigsItemClickListener;
import alekseybykov.pets.mg.gui.components.listeners.help.ShowHelpDialogItemClickListener;
import alekseybykov.pets.mg.gui.components.listeners.menu.ClearSelectedFilesItemClickListener;
import alekseybykov.pets.mg.gui.components.listeners.menu.ClearSenderLogItemClickListener;
import alekseybykov.pets.mg.gui.components.listeners.oebsimport.OEBSImportButtonClickListener;
import alekseybykov.pets.mg.gui.uiconsts.menus.ActionsMenuItems;
import alekseybykov.pets.mg.gui.uiconsts.menus.AdminMenuItems;
import alekseybykov.pets.mg.gui.uiconsts.menus.HelpMenuItems;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;

/**
 * @author bykov.alexey
 * @since 29.12.2022
 */
@Component
public class MenuBar extends JMenuBar {

	@Autowired
	private OEBSImportButtonClickListener oebsImportButtonClickListener;
	@Autowired
	private ClearSelectedFilesItemClickListener clearSelectedFilesItemClickListener;
	@Autowired
	private ClearSenderLogItemClickListener clearSenderLogItemClickListener;
	@Autowired
	private DBConnectionConfigsItemClickListener dbConnectionConfigsItemClickListener;
	@Autowired
	private LocalPathsItemClickListener localPathsItemClickListener;
	@Autowired
	private GuiThemesItemClickListener guiThemesItemClickListener;

	@PostConstruct
	private void buildMenu() {
		add(buildActionsMenu());
		add(buildAdminMenu());
		add(buildHelpMenu());
	}

	private JMenu buildActionsMenu() {
		JMenu menu = new JMenu(ActionsMenuItems.TITLE.getText());

		JMenuItem startMsgGeneratorItem = new JMenuItem(ActionsMenuItems.START_OAGIS_IMPORTING_ITEM.getText());
		startMsgGeneratorItem.addActionListener(oebsImportButtonClickListener);
		menu.add(startMsgGeneratorItem);

		menu.addSeparator();

		JMenuItem clearSelectedFilesItem = new JMenuItem(ActionsMenuItems.CLEAR_ATTACHMENTS.getText());
		clearSelectedFilesItem.addActionListener(clearSelectedFilesItemClickListener);
		menu.add(clearSelectedFilesItem);

		JMenuItem clearSenderLogItem = new JMenuItem(ActionsMenuItems.CLEAR_SENDER_LOG.getText());
		clearSenderLogItem.addActionListener(clearSenderLogItemClickListener);
		menu.add(clearSenderLogItem);

		return menu;
	}

	private JMenu buildAdminMenu() {
		JMenu menu = new JMenu(AdminMenuItems.TITLE.getText());

		JMenuItem showStandsConfigItem = new JMenuItem(AdminMenuItems.STANDS_CONFIG.getText());
		showStandsConfigItem.addActionListener(dbConnectionConfigsItemClickListener);
		menu.add(showStandsConfigItem);

		JMenuItem showLocalPathsItem = new JMenuItem(AdminMenuItems.LOCAL_PATHS_CONFIG.getText());
		showLocalPathsItem.addActionListener(localPathsItemClickListener);
		menu.add(showLocalPathsItem);

		JMenuItem showGuiThemesItem = new JMenuItem(AdminMenuItems.GUI_THEMES.getText());
		showGuiThemesItem.addActionListener(guiThemesItemClickListener);
		menu.add(showGuiThemesItem);

		return menu;
	}

	private JMenu buildHelpMenu() {
		JMenu menu = new JMenu(HelpMenuItems.TITLE.getText());

		JMenuItem showInstructionItem = new JMenuItem(HelpMenuItems.INSTRUCTION_ITEM.getText());
		showInstructionItem.addActionListener(new ShowHelpDialogItemClickListener(new InformationDialog()));
		menu.add(showInstructionItem);

		JMenuItem showQuickHelpItem = new JMenuItem(HelpMenuItems.QUICK_HELP_ITEM.getText());
		showQuickHelpItem.addActionListener(new ShowHelpDialogItemClickListener(new AboutDialog()));
		menu.add(showQuickHelpItem);

		return menu;
	}
}
