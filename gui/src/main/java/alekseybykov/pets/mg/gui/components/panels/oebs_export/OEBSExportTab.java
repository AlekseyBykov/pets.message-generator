package alekseybykov.pets.mg.gui.components.panels.oebs_export;

import alekseybykov.pets.mg.gui.components.buttons.OEBSExportButton;
import alekseybykov.pets.mg.gui.components.buttons.choose.ChooseTFFFileButton;
import alekseybykov.pets.mg.gui.components.panels.combobox.TransportFoldersComboBoxPanel;
import alekseybykov.pets.mg.gui.components.progressbar.OEBSExportProgressBar;
import alekseybykov.pets.mg.gui.components.tables.tff.TFFFileListTable;
import alekseybykov.pets.mg.gui.uiconsts.borders.BorderTitles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * @author bykov.alexey
 * @since 16.02.2024
 */
@Component
public class OEBSExportTab extends JPanel {

	@Autowired
	private OEBSExportButton oebsExportButton;
	@Autowired
	private ChooseTFFFileButton chooseTFFFileButton;
	@Autowired
	private OEBSExportProgressBar progressBar;
	@Autowired
	private TransportFoldersComboBoxPanel comboBoxPanel;
	@Autowired
	private TFFFileListTable tffFileListTable;

	@PostConstruct
	private void postConstruct() {
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(10, 10, 10, 10));

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JPanel toolbarPanel = new JPanel();
		toolbarPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolbarPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		JPanel tffChooserPanel = new JPanel();
		tffChooserPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		tffChooserPanel.add(chooseTFFFileButton);
		toolbarPanel.add(tffChooserPanel);

		toolbarPanel.add(oebsExportButton);
		toolbarPanel.add(comboBoxPanel);

		JPanel commonPanel = new JPanel();
		commonPanel.setLayout(new BorderLayout());

		JPanel tffListTablePanel = new JPanel();
		tffListTablePanel.setLayout(new BorderLayout());
		tffListTablePanel.setBorder(BorderFactory.createTitledBorder(BorderTitles.TFF_LIST_TABLE.getTitle()));
		tffListTablePanel.add(new JScrollPane(tffFileListTable), BorderLayout.CENTER);

		commonPanel.add(tffListTablePanel, BorderLayout.CENTER);

		JPanel statusBarPanel = new JPanel();
		statusBarPanel.setLayout(new BoxLayout(statusBarPanel, BoxLayout.X_AXIS));
		statusBarPanel.add(progressBar);

		panel.add(toolbarPanel);
		panel.add(commonPanel);
		panel.add(statusBarPanel);

		add(panel, BorderLayout.CENTER);
	}
}
