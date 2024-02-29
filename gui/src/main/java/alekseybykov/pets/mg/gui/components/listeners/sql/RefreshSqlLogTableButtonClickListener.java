package alekseybykov.pets.mg.gui.components.listeners.sql;

import alekseybykov.pets.mg.core.businessobjects.PageableData;
import alekseybykov.pets.mg.core.models.table.SqlLogTableModel;
import alekseybykov.pets.mg.gui.components.panels.sqllog.SqlLogSearchPanel;
import alekseybykov.pets.mg.gui.components.panels.sqllog.SqlLogTablePanel;
import alekseybykov.pets.mg.gui.components.progressbar.SQLLogProgressBar;
import alekseybykov.pets.mg.gui.uiconsts.Paginators;
import alekseybykov.pets.mg.services.paginator.PageableDataService;
import lombok.SneakyThrows;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
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
 * @since 26.06.2022
 */
@Component
public class RefreshSqlLogTableButtonClickListener implements ActionListener {

	@Autowired
	@Qualifier("SqlLogPaginatorService")
	private PageableDataService backendService;
	@Autowired
	private SqlLogTableModel tableModel;

	@Autowired
	private SqlLogTablePanel tablePanel;
	@Autowired
	private SQLLogProgressBar progressBar;
	@Autowired
	private SqlLogSearchPanel sqlLogSearchPanel;

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		loadPaginatorData(progressBar, tableModel);
	}

	private void loadPaginatorData(final JProgressBar progressBar,
	                               final SqlLogTableModel tableModel) {
		progressBar.setVisible(true);

		val dataLoader = new SwingWorker<Boolean, Void>() {
			int totalRowCount;
			@Override
			protected Boolean doInBackground() {
				totalRowCount = backendService.findRowsCount();
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

	private void loadTableDataRange(final SqlLogTableModel tableModel,
	                                final JProgressBar progressBar) {
		val dataLoader = new SwingWorker<Boolean, Void>() {
			List<PageableData> rows = Collections.emptyList();
			@Override
			protected Boolean doInBackground() {
				rows = backendService.findRowsRange(Paginators.START_PAGE_NUMBER.getValue(),
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
						sqlLogSearchPanel.getSqlLexemTextField().setText(StringUtils.EMPTY);
					}
				} finally {
					progressBar.setVisible(false);
				}
			}
		};
		dataLoader.execute();
	}
}
