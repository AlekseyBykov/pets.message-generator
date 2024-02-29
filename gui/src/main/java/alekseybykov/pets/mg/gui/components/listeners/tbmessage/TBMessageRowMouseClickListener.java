package alekseybykov.pets.mg.gui.components.listeners.tbmessage;

import alekseybykov.pets.mg.core.coreconsts.Timeouts;
import alekseybykov.pets.mg.core.models.encoding.TBMessageEncodingModel;
import alekseybykov.pets.mg.core.models.logtoggle.LogToggleModel;
import alekseybykov.pets.mg.core.xml.formatters.XmlFormatter;
import alekseybykov.pets.mg.gui.components.blobviewers.TBMessageBlobViewer;
import alekseybykov.pets.mg.gui.components.progressbar.TBMessageProgressBar;
import alekseybykov.pets.mg.services.tbmessage.TBMessageService;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.charset.Charset;

/**
 * @author bykov.alexey
 * @since 15.03.2021
 */
@Component
public class TBMessageRowMouseClickListener extends MouseAdapter {

	private static final int OUT_OF_THE_RANGE_ROW = -1;
	private static final int DOUBLE_CLICK = 2;

	@Autowired
	private TBMessageService service;
	@Autowired
	private TBMessageEncodingModel encodingModel;
	@Autowired
	private LogToggleModel logToggleModel;
	@Autowired
	private TBMessageBlobViewer blobViewer;

	@Setter
	private JPopupMenu popupMenu;

	@Autowired
	private TBMessageProgressBar progressBar;

	@Override
	@SneakyThrows
	public void mousePressed(MouseEvent mouseEvent) {
		JTable table = (JTable) mouseEvent.getSource();
		val selectedRow = table.rowAtPoint(mouseEvent.getPoint());

		if (selectedRow == OUT_OF_THE_RANGE_ROW) {
			return;
		}

		if (mouseEvent.getClickCount() == DOUBLE_CLICK) {
			progressBar.setVisible(true);

			val recordId = Integer.parseInt((String) table.getValueAt(selectedRow, 0));
			SwingWorker<Boolean, Void> blobLoader = new SwingWorker<Boolean, Void>() {
				String blobContent = StringUtils.EMPTY;

				@Override
				@SneakyThrows
				protected Boolean doInBackground() {
					// Удаляем предыдущее содержимое из фрейма просмотра XML.
					blobViewer.setText(StringUtils.EMPTY);

					Thread.sleep(Timeouts.GUI_PROGRESSBAR_TIMEOUT.getValue());
					blobContent = XmlFormatter.format(service.findFileById(recordId, Charset.forName(encodingModel.getSelectedEncoding())));
					return true;
				}

				@Override
				@SneakyThrows
				protected void done() {
					try {
						if (get() == Boolean.TRUE) {
							blobViewer.setText(blobContent);
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

	/**
	 * Мметод вызывает контекстное меню по правому клику мышью.
	 *
	 * @param mouseEvent
	 */
	@Override
	public void mouseReleased(MouseEvent mouseEvent) {
		JTable table = (JTable) mouseEvent.getSource();
		val selectedRow = table.rowAtPoint(mouseEvent.getPoint());
		if (selectedRow >= 0 && selectedRow < table.getRowCount()) {
			table.setRowSelectionInterval(selectedRow, selectedRow);
		} else {
			table.clearSelection();
		}

		val rowIndex = table.getSelectedRow();
		if (rowIndex < 0) {
			return;
		}

		if (mouseEvent.isPopupTrigger() && mouseEvent.getComponent() instanceof JTable) {
			popupMenu.show(mouseEvent.getComponent(), mouseEvent.getX(), mouseEvent.getY());
		}
	}
}
