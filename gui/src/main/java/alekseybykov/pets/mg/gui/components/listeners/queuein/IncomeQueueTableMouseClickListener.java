package alekseybykov.pets.mg.gui.components.listeners.queuein;

import alekseybykov.pets.mg.core.coreconsts.Timeouts;
import alekseybykov.pets.mg.core.models.encoding.IncomeQueueEncodingModel;
import alekseybykov.pets.mg.core.xml.formatters.XmlFormatter;
import alekseybykov.pets.mg.gui.components.blobviewers.IncomeQueueBlobViewer;
import alekseybykov.pets.mg.gui.components.progressbar.IncomeQueueProgressBar;
import alekseybykov.pets.mg.services.queuein.IncomeQueueService;
import lombok.SneakyThrows;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author bykov.alexey
 * @since 18.04.2021
 */
@Component
public class IncomeQueueTableMouseClickListener extends MouseAdapter {

	@Autowired
	private IncomeQueueService backendService;
	@Autowired
	private IncomeQueueEncodingModel encodingModel;
	@Autowired
	private IncomeQueueProgressBar progressBar;
	@Autowired
	private IncomeQueueBlobViewer blobViewer;

	@Override
	public void mousePressed(MouseEvent mouseEvent) {
		JTable table = (JTable) mouseEvent.getSource();
		val selectedRow = table.rowAtPoint(mouseEvent.getPoint());
		if (mouseEvent.getClickCount() == 2 && selectedRow != NumberUtils.INTEGER_MINUS_ONE) {
			final int recordId = (int) table.getValueAt(selectedRow, NumberUtils.INTEGER_ZERO);
			if (recordId == 0) {
				return;
			}

			progressBar.setVisible(true);

			val blobLoader = new SwingWorker<Boolean, Void>() {
				String fileContent = StringUtils.EMPTY;

				@Override
				@SneakyThrows
				protected Boolean doInBackground() {
					Thread.sleep(Timeouts.GUI_PROGRESSBAR_TIMEOUT.getValue());
					fileContent = XmlFormatter.format(backendService.findFileById(recordId, encodingModel.getSelectedEncoding()));
					return true;
				}

				@Override
				@SneakyThrows
				protected void done() {
					try {
						if (get() == Boolean.TRUE) {
							blobViewer.setText(fileContent);
							blobViewer.setCaretPosition(NumberUtils.INTEGER_ZERO);
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
