package alekseybykov.pets.mg.gui.components.listeners.tbmessage;

import alekseybykov.pets.mg.core.businessobjects.PageableData;
import alekseybykov.pets.mg.core.models.table.TBMessageTableModel;
import alekseybykov.pets.mg.gui.components.panels.tbmessage.TBMessageTablePanel;
import alekseybykov.pets.mg.gui.components.progressbar.TBMessageProgressBar;
import alekseybykov.pets.mg.gui.uiconsts.Paginators;
import alekseybykov.pets.mg.services.paginator.PageableDataService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;

/**
 * @author bykov.alexey
 * @since 14.03.2021
 */
@Component
public class RefreshTBMessageTableButtonClickListener implements ActionListener {

	@Autowired
	@Qualifier("TBMessagePaginatorService")
	private PageableDataService service;
	@Autowired
	private TBMessageTablePanel tablePanel;
	@Autowired
	private TBMessageTableModel tableModel;

	@Autowired
	private TBMessageProgressBar progressBar;

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		loadPaginatorData(progressBar, tableModel);
	}

	private void loadPaginatorData(final JProgressBar progressBar,
	                               final TBMessageTableModel tableModel) {
		progressBar.setVisible(true);
		SwingWorker<Boolean, Void> dataLoader = new SwingWorker<Boolean, Void>() {
			int totalRowCount;
			@Override
			protected Boolean doInBackground() {
				totalRowCount = service.findRowsCount();
				return true;
			}

			@Override
			@SneakyThrows
			protected void done() {
				if (get() == Boolean.TRUE) {
					loadTableDataRange(tableModel, progressBar);
					tablePanel.updatePaginator(totalRowCount);
				}
			}
		};
		dataLoader.execute();
	}

	private void loadTableDataRange(final TBMessageTableModel tableModel,
	                                final JProgressBar progressBar) {
		SwingWorker<Boolean, Void> dataLoader = new SwingWorker<Boolean, Void>() {
			List<PageableData> rows = Collections.emptyList();
			@Override
			protected Boolean doInBackground() {
				rows = service.findRowsRange(Paginators.START_PAGE_NUMBER.getValue(),
						Paginators.END_PAGE_NUMBER.getValue());
				return true;
			}

			@Override
			@SneakyThrows
			protected void done() {
				try {
					if (get() == Boolean.TRUE) {
						tableModel.setData(rows);
						tableModel.updateTable();
					}
				} finally {
					progressBar.setVisible(false);
				}
			}
		};
		dataLoader.execute();
	}
}
