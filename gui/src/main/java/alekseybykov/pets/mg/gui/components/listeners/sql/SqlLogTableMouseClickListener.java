package alekseybykov.pets.mg.gui.components.listeners.sql;

import com.github.vertical_blank.sqlformatter.SqlFormatter;
import alekseybykov.pets.mg.core.coreconsts.Timeouts;
import alekseybykov.pets.mg.core.models.encoding.SqlLogEncodingModel;
import alekseybykov.pets.mg.gui.components.blobviewers.SQLViewer;
import alekseybykov.pets.mg.gui.components.progressbar.SQLLogProgressBar;
import alekseybykov.pets.mg.services.sql.SqlLogService;
import lombok.SneakyThrows;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Component
public class SqlLogTableMouseClickListener extends MouseAdapter {

	@Autowired
	private SqlLogService backendService;
	@Autowired
	private SqlLogEncodingModel encodingModel;
	@Autowired
	private SQLViewer viewer;
	@Autowired
	private SQLLogProgressBar progressBar;

	@Override
	public void mousePressed(MouseEvent mouseEvent) {
		val table = (JTable) mouseEvent.getSource();
		int selectedRow = table.getSelectedRow();
		if (mouseEvent.getClickCount() == 2 && selectedRow != -1) {
			final String recordId = (String) table.getValueAt(selectedRow, 0);
			if (StringUtils.isEmpty(recordId)) {
				return;
			}

			progressBar.setVisible(true);

			val blobLoader = new SwingWorker<Boolean, Void>() {
				String sqlText = StringUtils.EMPTY;

				@Override
				@SneakyThrows
				protected Boolean doInBackground() {
					Thread.sleep(Timeouts.GUI_PROGRESSBAR_TIMEOUT.getValue());
					sqlText = SqlFormatter.format(
							backendService.findSqlTextById(
									recordId,
									encodingModel.getSelectedEncoding()
							)
					);
					return true;
				}

				@Override
				@SneakyThrows
				protected void done() {
					try {
						if (get() == Boolean.TRUE) {
							viewer.setText(sqlText);
							viewer.setCaretPosition(NumberUtils.INTEGER_ZERO);
						}
					} finally {
						progressBar.setVisible(false);
					}
				}
			};
			blobLoader.execute();
		}
	}
}
