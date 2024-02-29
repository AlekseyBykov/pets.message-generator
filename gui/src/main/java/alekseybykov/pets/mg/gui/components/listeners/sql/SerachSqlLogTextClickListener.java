package alekseybykov.pets.mg.gui.components.listeners.sql;

import alekseybykov.pets.mg.core.businessobjects.PageableData;
import alekseybykov.pets.mg.core.models.table.SqlLogTableModel;
import alekseybykov.pets.mg.gui.components.panels.sqllog.SqlLogBrowserTab;
import alekseybykov.pets.mg.gui.components.panels.sqllog.SqlLogSearchPanel;
import alekseybykov.pets.mg.gui.components.panels.sqllog.SqlLogTablePanel;
import alekseybykov.pets.mg.gui.components.progressbar.SQLLogProgressBar;
import alekseybykov.pets.mg.gui.uiconsts.Paginators;
import alekseybykov.pets.mg.services.sql.SqlLogService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * @author bykov.alexey
 * @since 26.06.2022
 */
@Component
public class SerachSqlLogTextClickListener implements ActionListener {

	@Autowired
	private SqlLogBrowserTab browserTab;
	@Autowired
	private SqlLogTablePanel sqlLogTablePanel;
	@Autowired
	private SqlLogSearchPanel sqlLogSearchPanel;
	@Autowired
	private SQLLogProgressBar progressBar;

	@Autowired
	private SqlLogService backendService;
	@Autowired
	private SqlLogTableModel tableModel;

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		progressBar.setVisible(true);
		SwingWorker<Boolean, Void> dataLoader = new SwingWorker<Boolean, Void>() {
			List<PageableData> rows = new ArrayList<>();

			@Override
			protected Boolean doInBackground() {
				JTextField sqlLexemTextField = sqlLogSearchPanel.getSqlLexemTextField();
				rows = backendService.findAllRowsBySqlSubstring(sqlLexemTextField.getText(), Paginators.START_PAGE_NUMBER.getValue(),
						Paginators.END_PAGE_NUMBER.getValue());

				sqlLogTablePanel.updatePaginator(rows.size());

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
