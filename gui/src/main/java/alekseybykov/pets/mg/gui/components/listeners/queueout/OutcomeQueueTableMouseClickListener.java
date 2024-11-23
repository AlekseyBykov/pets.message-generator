package alekseybykov.pets.mg.gui.components.listeners.queueout;

import alekseybykov.pets.mg.core.coreconsts.Timeouts;
import alekseybykov.pets.mg.core.models.encoding.OutcomeQueueEncodingModel;
import alekseybykov.pets.mg.core.xml.formatters.XmlFormatter;
import alekseybykov.pets.mg.gui.components.blobviewers.OutcomeQueueBlobViewer;
import alekseybykov.pets.mg.gui.components.progressbar.OutcomeQueueProgressBar;
import alekseybykov.pets.mg.services.queueout.OutcomeQueueService;
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
public class OutcomeQueueTableMouseClickListener extends MouseAdapter {

	@Autowired
	private OutcomeQueueService backendService;
	@Autowired
	private OutcomeQueueEncodingModel encodingModel;
	@Autowired
	private OutcomeQueueProgressBar progressBar;
	@Autowired
	private OutcomeQueueBlobViewer blobViewer;

	@Override
	public void mousePressed(MouseEvent mouseEvent) {
		val table = (JTable) mouseEvent.getSource();
		val selectedRow = table.rowAtPoint(mouseEvent.getPoint());
		if (mouseEvent.getClickCount() == 2 && selectedRow != -1) {
			final int recordId = (int) table.getValueAt(selectedRow, 0);
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
					fileContent = XmlFormatter.format(
							backendService.findFileById(
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
