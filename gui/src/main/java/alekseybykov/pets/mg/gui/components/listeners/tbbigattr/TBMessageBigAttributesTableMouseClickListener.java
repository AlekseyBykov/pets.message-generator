package alekseybykov.pets.mg.gui.components.listeners.tbbigattr;

import alekseybykov.pets.mg.core.coreconsts.Timeouts;
import alekseybykov.pets.mg.core.models.encoding.TBMessageBigAttributesEncodingModel;
import alekseybykov.pets.mg.core.xml.formatters.XmlFormatter;
import alekseybykov.pets.mg.gui.components.blobviewers.TBMessageBigAttributesBlobViewer;
import alekseybykov.pets.mg.gui.components.progressbar.TBMessageBigAttributesProgressBar;
import alekseybykov.pets.mg.services.tbbigattr.TBMessageBigAttributesService;
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
 * @since 18.03.2021
 */
@Component
public class TBMessageBigAttributesTableMouseClickListener extends MouseAdapter {

	@Autowired
	private TBMessageBigAttributesService backendService;
	@Autowired
	private TBMessageBigAttributesEncodingModel encodingModel;
	@Autowired
	private TBMessageBigAttributesProgressBar progressBar;
	@Autowired
	private TBMessageBigAttributesBlobViewer blobViewer;

	@Override
	public void mousePressed(MouseEvent mouseEvent) {
		JTable table = (JTable) mouseEvent.getSource();
		val selectedRow = table.rowAtPoint(mouseEvent.getPoint());
		if (mouseEvent.getClickCount() == 2 && selectedRow != NumberUtils.INTEGER_MINUS_ONE) {
			final Integer recordId = (Integer) table.getValueAt(selectedRow, NumberUtils.INTEGER_ZERO);
			if (recordId == null) {
				return;
			}

			progressBar.setVisible(true);

			SwingWorker<Boolean, Void> blobLoader = new SwingWorker<Boolean, Void>() {
				String xml = StringUtils.EMPTY;

				@Override
				@SneakyThrows
				protected Boolean doInBackground() {
					// Удаляем предыдущее содержимое из фрейма просмотра XML.
					blobViewer.setText(StringUtils.EMPTY);

					Thread.sleep(Timeouts.GUI_PROGRESSBAR_TIMEOUT.getValue());
					xml = XmlFormatter.format(backendService.findFileById(recordId, encodingModel.getSelectedEncoding()));

					return true;
				}

				@Override
				@SneakyThrows
				protected void done() {
					try {
						if (get() == Boolean.TRUE) {
							blobViewer.setText(xml);
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
