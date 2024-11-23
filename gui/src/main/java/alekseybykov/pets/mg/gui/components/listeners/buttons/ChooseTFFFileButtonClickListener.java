package alekseybykov.pets.mg.gui.components.listeners.buttons;

import alekseybykov.pets.mg.core.businessobjects.file.TFFFile;
import alekseybykov.pets.mg.core.coreconsts.ErrorMessages;
import alekseybykov.pets.mg.core.coreconsts.Properties;
import alekseybykov.pets.mg.core.models.table.TFFFileListTableModel;
import alekseybykov.pets.mg.services.localpath.LocalPathService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

@Component
public class ChooseTFFFileButtonClickListener extends ChooseFileButtonClickListener {

	@Autowired
	private TFFFileListTableModel tffFileListTableModel;
	@Autowired
	private LocalPathService localPathService;

	@Override
	protected void processButtonClickEvent() {
		val fileChooser = buildFileChooser();
		val currentDirectory = findCurrentDirectory();
		fileChooser.setCurrentDirectory(new File(currentDirectory));

		fileChooser.setFileFilter(new FileNameExtensionFilter("*.xml file", "xml"));

		if (fileChooser.showDialog(null, "Выбрать xml-файл") != JFileChooser.APPROVE_OPTION ) {
			return;
		}

		if (!fileChooser.getSelectedFile().exists()) {
			JOptionPane.showMessageDialog(fileChooser,
					ErrorMessages.FILE_CHOOSER_ERROR_MESSAGE.getName(),
					"Ошибка при открытии файла",
					JOptionPane.ERROR_MESSAGE);
		} else {
			val fileName = fileChooser.getSelectedFile().getName();
			val absolutePath = fileChooser.getSelectedFile().getAbsolutePath();

			localPathService.updateLocalPath("TFF_HOME", absolutePath);

			TFFFile tffFile = new TFFFile(fileName, fileChooser.getSelectedFile().length(), absolutePath);
			tffFileListTableModel.addTFF(tffFile);
		}
	}

	private String findCurrentDirectory() {
		val savedPreviousDir = localPathService.getLocalPathValue("TFF_HOME");
		return StringUtils.isEmpty(savedPreviousDir)
		       ? System.getProperty(Properties.USER_HOME_PROPERTY.getName())
		       : savedPreviousDir;
	}
}
