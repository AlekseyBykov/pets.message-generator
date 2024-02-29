package alekseybykov.pets.mg.gui.components.listeners.oebsimport;

import alekseybykov.pets.mg.core.businessobjects.file.OAGISFile;
import alekseybykov.pets.mg.core.coreconsts.Timeouts;
import alekseybykov.pets.mg.core.logging.UILogger;
import alekseybykov.pets.mg.core.models.table.AttachmentFileListTableModel;
import alekseybykov.pets.mg.core.models.table.OAGISFileListTableModel;
import alekseybykov.pets.mg.gui.components.buttons.OEBSImportButton;
import alekseybykov.pets.mg.gui.components.progressbar.OEBSImportProgressBar;
import alekseybykov.pets.mg.services.oebs_import.OEBSImportService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author bykov.alexey
 * @since 29.04.2016
 */
@Slf4j
@Component
public class OEBSImportButtonClickListener implements ActionListener {

	private static final UILogger uiLogger = UILogger.getInstance();

	@Autowired
	private OEBSImportProgressBar progressBar;
	@Autowired
	private OEBSImportButton oebsImportButton;

	@Autowired
	private OAGISFileListTableModel oagisFileListTableModel;
	@Autowired
	private AttachmentFileListTableModel attachmentFileListTableModel;
	@Autowired
	private OEBSImportService oebsImportService;

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		OAGISFile oagisFile = oagisFileListTableModel.getOagisFile();
		if (oagisFile == null) {
			Object[] dialogOptions = {"Ок"};
			JOptionPane.showOptionDialog(null, "Не выбран OAGIS файл", "Ошибка",
					JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, null, dialogOptions, null);
			log.error("Процесс генерации не был запущен, так как OAGIS файл не был выбран");
			return;
		}

		progressBar.setVisible(true);
		oebsImportButton.setEnabled(false);

		SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
			@Override
			@SneakyThrows
			protected Boolean doInBackground() {
				uiLogger.log("<font color=\"#742765\"><b>[процесс генерации запущен]</b></font><br/><br/>");
				Thread.sleep(Timeouts.GUI_PROGRESSBAR_TIMEOUT.getValue());

				oebsImportService.importOagisFileWithAttachmentFiles(oagisFile, attachmentFileListTableModel.getAttachmentFiles());
				return true;
			}

			@Override
			@SneakyThrows
			protected void done() {
				try {
					if (get()) {
						uiLogger.log("<font color=\"#138808\"><b>Процесс генерации завершен успешно</b></font><br/>");
					}
				} finally {
					progressBar.setVisible(false);
					oebsImportButton.setEnabled(true);
					uiLogger.log("<font color=\"#742765\"><b>[процесс генерации остановлен]</b></font><br/><br/>");
				}
			}
		};
		worker.execute();
	}
}
