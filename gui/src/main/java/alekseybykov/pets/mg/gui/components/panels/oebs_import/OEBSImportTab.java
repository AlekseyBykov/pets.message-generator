package alekseybykov.pets.mg.gui.components.panels.oebs_import;

import alekseybykov.pets.mg.gui.components.buttons.OEBSImportButton;
import alekseybykov.pets.mg.gui.components.buttons.choose.ChooseAttachmentFileButton;
import alekseybykov.pets.mg.gui.components.buttons.choose.ChooseOAGISFileButton;
import alekseybykov.pets.mg.gui.components.progressbar.OEBSImportProgressBar;
import alekseybykov.pets.mg.gui.components.tables.attachments.AttachmentFileListTable;
import alekseybykov.pets.mg.gui.components.tables.oagis.OAGISFileListTable;
import alekseybykov.pets.mg.gui.uiconsts.GUISize;
import alekseybykov.pets.mg.gui.uiconsts.borders.BorderTitles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * @author bykov.alexey
 * @since 17.02.2021
 */
@Component
public class OEBSImportTab extends JPanel {

	@Autowired
	private ChooseOAGISFileButton chooseOAGISFileButton;
	@Autowired
	private ChooseAttachmentFileButton chooseAttachmentFileButton;
	@Autowired
	private OEBSImportButton oebsImportButton;

	@Autowired
	private OAGISFileListTable oagisFileListTable;
	@Autowired
	private AttachmentFileListTable attachmentFileListTable;
	@Autowired
	private OEBSImportProgressBar progressBar;

	@PostConstruct
	private void postConstruct() {
		composeToolbar();
	}

	private void composeToolbar() {
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(10, 10, 10, 10));

		JPanel composedToolbarPanel = new JPanel();
		composedToolbarPanel.setLayout(new BoxLayout(composedToolbarPanel, BoxLayout.Y_AXIS));

		composedToolbarPanel.add(buildButtonsPanel());
		composedToolbarPanel.add(buildTablesPanel());
		composedToolbarPanel.add(buildProgressBarPanel());

		add(composedToolbarPanel, BorderLayout.CENTER);
	}

	private JPanel buildProgressBarPanel() {
		JPanel progressBarPanel = new JPanel();
		progressBarPanel.setLayout(new BoxLayout(progressBarPanel, BoxLayout.X_AXIS));
		progressBarPanel.add(progressBar);
		return progressBarPanel;
	}

	private JPanel buildTablesPanel() {
		JPanel tablesPanel = new JPanel();
		tablesPanel.setLayout(new BorderLayout());
		tablesPanel.add(buildOAGISDetailsTable(), BorderLayout.NORTH);
		tablesPanel.add(buildAttachmentListTable(), BorderLayout.CENTER);
		return tablesPanel;
	}

	private JPanel buildButtonsPanel() {
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		buttonsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		buttonsPanel.add(chooseOAGISFileButton);
		buttonsPanel.add(chooseAttachmentFileButton);
		buttonsPanel.add(oebsImportButton);
		return buttonsPanel;
	}

	private JPanel buildAttachmentListTable() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createTitledBorder(BorderTitles.ATTACHMENT_LIST_TABLE.getTitle()));
		panel.add(new JScrollPane(attachmentFileListTable), BorderLayout.CENTER);
		return panel;
	}

	private JPanel buildOAGISDetailsTable() {
		JPanel panel = new JPanel();
		// todo желательно использовать GridLayout вместо явного указания размеров.
		panel.setPreferredSize(
				new Dimension(GUISize.MAX_COMMON_WIDTH.getValue(), GUISize.ONE_ROW_TABLE_HEIGHT.getValue()));
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createTitledBorder(BorderTitles.SELECTED_OAGIS.getTitle()));
		panel.add(new JScrollPane(oagisFileListTable), BorderLayout.CENTER);
		return panel;
	}
}
