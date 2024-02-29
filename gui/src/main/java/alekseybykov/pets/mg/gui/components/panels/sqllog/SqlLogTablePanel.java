package alekseybykov.pets.mg.gui.components.panels.sqllog;

import alekseybykov.pets.mg.core.models.encoding.SqlLogEncodingModel;
import alekseybykov.pets.mg.core.models.table.SqlLogTableModel;
import alekseybykov.pets.mg.gui.components.buttons.refresh.RefreshSqlLogTableButton;
import alekseybykov.pets.mg.gui.components.paginators.Paginator;
import alekseybykov.pets.mg.gui.components.panels.common.EncodingView;
import alekseybykov.pets.mg.gui.components.progressbar.SQLLogProgressBar;
import alekseybykov.pets.mg.gui.components.tables.sqllog.SqlLogTable;
import alekseybykov.pets.mg.services.paginator.PageableDataService;
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
 * @since 26.06.2022
 */
@Component
public class SqlLogTablePanel extends JPanel {

	@Autowired
	private RefreshSqlLogTableButton refreshSqlLogTableButton;

	@Autowired
	@Qualifier("SqlLogPaginatorService")
	private PageableDataService backendService;

	@Autowired
	private SqlLogTable table;
	@Autowired
	private SqlLogTableModel tableModel;
	@Autowired
	private EncodingView encodingView;
	@Autowired
	private SqlLogEncodingModel encodingModel;
	@Autowired
	private SqlLogSearchPanel sqlLogSearchPanel;

	@Autowired
	private SQLLogProgressBar progressBar;

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

		val panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		panel.add(buildButtonsPanel());
		panel.add(buildScrolledTablePanel());
		panel.add(buildStatusPanel());

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
		buttonsPanel.add(refreshSqlLogTableButton);
		buttonsPanel.add(encodingView);
		buttonsPanel.add(sqlLogSearchPanel);

		buttonsPanel.add(paginatorPanel);
		return buttonsPanel;
	}

	private JPanel buildStatusPanel() {
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
