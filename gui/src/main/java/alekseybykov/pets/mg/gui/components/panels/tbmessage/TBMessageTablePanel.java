package alekseybykov.pets.mg.gui.components.panels.tbmessage;

import alekseybykov.pets.mg.core.models.encoding.TBMessageEncodingModel;
import alekseybykov.pets.mg.core.models.table.TBMessageTableModel;
import alekseybykov.pets.mg.gui.components.buttons.refresh.RefreshTBMessageTableButton;
import alekseybykov.pets.mg.gui.components.buttons.toggle.TBMessageLogToggleButton;
import alekseybykov.pets.mg.gui.components.paginators.Paginator;
import alekseybykov.pets.mg.gui.components.panels.common.EncodingView;
import alekseybykov.pets.mg.gui.components.panels.common.LogToggleView;
import alekseybykov.pets.mg.gui.components.progressbar.TBMessageProgressBar;
import alekseybykov.pets.mg.gui.components.tables.tbmessage.TBMessageTable;
import alekseybykov.pets.mg.services.paginator.PageableDataService;
import lombok.Getter;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * @author bykov.alexey
 * @since 14.03.2021
 */
@Getter
@Component
public class TBMessageTablePanel extends JPanel {

	@Autowired
	private RefreshTBMessageTableButton refreshTBMessageTableButton;

	@Autowired
	@Qualifier("TBMessagePaginatorService")
	private PageableDataService backendService;

	@Autowired
	private TBMessageTable table;
	@Autowired
	private TBMessageTableModel tableModel;

	@Autowired
	private TBMessageLogToggleButton logToggleButton;

	@Autowired
	private EncodingView encodingView;
	@Autowired
	private TBMessageEncodingModel encodingModel;
	@Autowired
	private LogToggleView logToggleView;

	@Autowired
	private TBMessageProgressBar progressBar;

	private JPanel paginatorPanel;

	@PostConstruct
	private void postConstruct() {
		initFields();
		buildLayout();
	}

	private void initFields() {
		paginatorPanel = new JPanel();
		encodingView.setModel(encodingModel);
	}

	private void buildLayout() {
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(10, 10, 10, 10));

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		panel.add(buildButtonsPanel());
		panel.add(buildScrolledTablePanel());
		panel.add(buildProgressBarPanel());

		add(panel, BorderLayout.CENTER);
	}

	private JPanel buildScrolledTablePanel() {
		val panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(new JScrollPane(table), BorderLayout.CENTER);
		return panel;
	}

	private JPanel buildButtonsPanel() {
		val buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		buttonsPanel.add(refreshTBMessageTableButton);
		buttonsPanel.add(encodingView);
		buttonsPanel.add(logToggleView);
		buttonsPanel.add(paginatorPanel);
		return buttonsPanel;
	}

	private JPanel buildProgressBarPanel() {
		val statusPanel = new JPanel();
		statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
		statusPanel.add(progressBar);
		return statusPanel;
	}

	public void updatePaginator(int totalRowsCount) {
		val paginator = new Paginator(backendService, tableModel, progressBar);
		paginator.init();
		paginator.setPaginatorPanel(paginatorPanel);
		paginator.buildPaginatorPanel(totalRowsCount);
	}
}
