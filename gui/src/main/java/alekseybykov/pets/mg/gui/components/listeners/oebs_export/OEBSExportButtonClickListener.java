package alekseybykov.pets.mg.gui.components.listeners.oebs_export;

import alekseybykov.pets.mg.core.businessobjects.TransportFolder;
import alekseybykov.pets.mg.core.businessobjects.file.TFFFile;
import alekseybykov.pets.mg.core.coreconsts.Timeouts;
import alekseybykov.pets.mg.core.logging.UILogger;
import alekseybykov.pets.mg.core.models.combobox.TransportFolderComboBoxModel;
import alekseybykov.pets.mg.core.models.table.TFFFileListTableModel;
import alekseybykov.pets.mg.gui.components.buttons.OEBSExportButton;
import alekseybykov.pets.mg.gui.components.progressbar.OEBSExportProgressBar;
import alekseybykov.pets.mg.services.filefolder.FileFolderService;
import alekseybykov.pets.mg.services.templating.TemplatingService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class OEBSExportButtonClickListener implements ActionListener {

	private static final UILogger uiLogger = UILogger.getInstance();

	@Autowired
	private TFFFileListTableModel tffFileListTableModel;
	@Autowired
	private TransportFolderComboBoxModel transportFolderComboBoxModel;
	@Autowired
	private TemplatingService templatingService;
	@Autowired
	private FileFolderService fileFolderService;

	@Autowired
	private OEBSExportProgressBar progressBar;
	@Autowired
	private OEBSExportButton oebsExportButton;

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		List<TFFFile> tffFiles = tffFileListTableModel.getTffFiles();
		if (CollectionUtils.isEmpty(tffFiles)) {
			Object[] dialogOptions = {"Ок"};
			JOptionPane.showOptionDialog(
					null,
					"Не выбраны ТФФ файлы",
					"Ошибка",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.ERROR_MESSAGE,
					null,
					dialogOptions,
					null
			);
			log.error("Необходимо выбрать ТФФ файлы, которые будут отправлены в транспортный каталог");
			return;
		}

		TransportFolder transportFolder = transportFolderComboBoxModel.getSelectedItem();
		if (transportFolder == null) {
			Object[] dialogOptions = {"Ок"};
			JOptionPane.showOptionDialog(
					null,
					"Не выбран транспортный каталог",
					"Ошибка",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.ERROR_MESSAGE,
					null,
					dialogOptions,
					null
			);
			log.error("Необходимо выбрать транспортный каталог, в который будет отправлен ТФФ файл");
			return;
		}

		progressBar.setVisible(true);
		oebsExportButton.setEnabled(false);

		val worker = new SwingWorker<Boolean, Void>() {
			@Override
			@SneakyThrows
			protected Boolean doInBackground() {
				uiLogger.log("<font color=\"#742765\"><b>[процесс генерации запущен]</b></font><br/><br/>");
				Thread.sleep(Timeouts.GUI_PROGRESSBAR_TIMEOUT.getValue());

				TransportFolder transportFolder = transportFolderComboBoxModel.getSelectedItem();
				Map<String, File> processedFiles = templatingService.process(tffFiles);
				fileFolderService.sendFilesToTransportFolder(processedFiles, transportFolder);

				return true;
			}

			@Override
			@SneakyThrows
			protected void done() {
				try {
					if (get()) {
						uiLogger.log(
								"<font color=\"#138808\"><b>Процесс генерации завершен успешно</b></font><br/>"
						);
					}
				} finally {
					progressBar.setVisible(false);
					oebsExportButton.setEnabled(true);
					uiLogger.log(
							"<font color=\"#742765\"><b>[процесс генерации остановлен]</b></font><br/><br/>"
					);
				}
			}
		};
		worker.execute();
	}
}
