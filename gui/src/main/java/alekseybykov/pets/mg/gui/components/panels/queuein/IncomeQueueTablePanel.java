package alekseybykov.pets.mg.gui.components.panels.queuein;

import alekseybykov.pets.mg.core.models.encoding.IncomeQueueEncodingModel;
import alekseybykov.pets.mg.core.models.table.IncomeQueueTableModel;
import alekseybykov.pets.mg.gui.components.buttons.refresh.RefreshIncomeQueueTableButton;
import alekseybykov.pets.mg.gui.components.paginators.Paginator;
import alekseybykov.pets.mg.gui.components.panels.common.EncodingView;
import alekseybykov.pets.mg.gui.components.progressbar.IncomeQueueProgressBar;
import alekseybykov.pets.mg.gui.components.tables.queue.IncomeQueueTable;
import alekseybykov.pets.mg.services.paginator.PageableDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

@Component
public class IncomeQueueTablePanel extends JPanel {

	@Autowired
	private RefreshIncomeQueueTableButton refreshIncomeQueueTableButton;

	@Autowired
	@Qualifier("IncomeQueuePaginatorService")
	private PageableDataService backendService;

	@Autowired
	private IncomeQueueTable table;
	@Autowired
	private EncodingView encodingView;
	@Autowired
	private IncomeQueueEncodingModel encodingModel;
	@Autowired
	private IncomeQueueTableModel tableModel;

	@Autowired
	private IncomeQueueProgressBar progressBar;

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
		panel.add(buildStatusPanel());

		add(panel, BorderLayout.CENTER);
	}

	private JPanel buildScrolledTablePanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(new JScrollPane(table), BorderLayout.CENTER);
		return panel;
	}

	private JPanel buildButtonsPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.add(refreshIncomeQueueTableButton);
		panel.add(encodingView);
		panel.add(paginatorPanel);
		return panel;
	}

	private JPanel buildStatusPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.add(progressBar);
		return panel;
	}

	public void updatePaginator(int totalRowsCount) {
		Paginator paginator = new Paginator(backendService, tableModel, progressBar);
		paginator.init();
		paginator.setPaginatorPanel(paginatorPanel);
		paginator.buildPaginatorPanel(totalRowsCount);
	}
}
