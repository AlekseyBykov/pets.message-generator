package alekseybykov.pets.mg.gui.components.panels.tbbigattr;

import alekseybykov.pets.mg.core.models.encoding.TBMessageBigAttributesEncodingModel;
import alekseybykov.pets.mg.core.models.table.TBMessageBigAttributesTableModel;
import alekseybykov.pets.mg.gui.components.buttons.refresh.RefreshTBMessageBigAttributesTableButton;
import alekseybykov.pets.mg.gui.components.paginators.Paginator;
import alekseybykov.pets.mg.gui.components.panels.common.EncodingView;
import alekseybykov.pets.mg.gui.components.progressbar.TBMessageBigAttributesProgressBar;
import alekseybykov.pets.mg.gui.components.tables.bigattr.TBMessageBigAttributesTable;
import alekseybykov.pets.mg.services.paginator.PageableDataService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * @author bykov.alexey
 * @since 18.03.2021
 */
@Getter
@Component
public class TBMessageBigAttributesTablePanel extends JPanel {

	@Autowired
	private RefreshTBMessageBigAttributesTableButton refreshTBMessageBigAttributesTableButton;

	@Autowired
	@Qualifier("TBMessageBigAttributesPaginatorService")
	private PageableDataService backendService;

	@Autowired
	private TBMessageBigAttributesTable table;
	@Autowired
	private TBMessageBigAttributesTableModel tableModel;

	@Autowired
	private EncodingView encodingView;
	@Autowired
	private TBMessageBigAttributesEncodingModel encodingModel;
	@Autowired
	private TBMessageBigAttributesProgressBar progressBar;

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
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		buttonsPanel.add(refreshTBMessageBigAttributesTableButton);
		buttonsPanel.add(encodingView);
		buttonsPanel.add(paginatorPanel);
		return buttonsPanel;
	}

	private JPanel buildStatusPanel() {
		JPanel statusPanel = new JPanel();
		statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
		statusPanel.add(progressBar);
		return statusPanel;
	}

	public void updatePaginator(int totalRowsCount) {
		Paginator paginator = new Paginator(backendService, tableModel, progressBar);
		paginator.init();
		paginator.setPaginatorPanel(paginatorPanel);
		paginator.buildPaginatorPanel(totalRowsCount);
	}
}
