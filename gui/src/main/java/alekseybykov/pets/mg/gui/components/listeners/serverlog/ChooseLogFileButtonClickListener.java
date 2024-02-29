package alekseybykov.pets.mg.gui.components.listeners.serverlog;

import alekseybykov.pets.mg.core.coreconsts.ErrorMessages;
import alekseybykov.pets.mg.core.coreconsts.Properties;
import alekseybykov.pets.mg.core.models.complexmodels.ServerToolbarModel;
import alekseybykov.pets.mg.gui.components.listeners.buttons.ChooseFileButtonClickListener;
import alekseybykov.pets.mg.gui.components.textfields.ServerLogFilePathTextField;
import alekseybykov.pets.mg.services.localpath.LocalPathService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

/**
 * @author bykov.alexey
 * @since 23.10.2023
 */
@Component
public class ChooseLogFileButtonClickListener extends ChooseFileButtonClickListener {

	@Autowired
	private ServerLogFilePathTextField serverLogFilePathTextField;
	@Autowired
	private ServerToolbarModel serverToolbarModel;
	@Autowired
	private LocalPathService localPathService;

	protected void processButtonClickEvent() {
		JFileChooser fileChooser = buildFileChooser();
		val currentDirectory = findCurrentDirectory();
		fileChooser.setCurrentDirectory(new File(currentDirectory));

		fileChooser.setFileFilter(new FileNameExtensionFilter("*.log file", "log"));

		if (fileChooser.showDialog(null, "Выбрать лог-файл") != JFileChooser.APPROVE_OPTION ) {
			return;
		}

		if (!fileChooser.getSelectedFile().exists()) {
			JOptionPane.showMessageDialog(fileChooser,
					ErrorMessages.FILE_CHOOSER_ERROR_MESSAGE.getName(),
					"Ошибка при открытии файла",
					JOptionPane.ERROR_MESSAGE);
		} else {
			val absolutePath = fileChooser.getSelectedFile().getAbsolutePath();
			val fileName = fileChooser.getSelectedFile().getName();

			localPathService.updateLocalPath("LOG_HOME", absolutePath);

			serverToolbarModel.setLogfileAbsolutePath(absolutePath);
			serverLogFilePathTextField.setText(absolutePath);
		}
	}

	private String findCurrentDirectory() {
		val savedPreviousDir = localPathService.getLocalPathValue("LOG_HOME");
		return StringUtils.isEmpty(savedPreviousDir)
		       ? System.getProperty(Properties.USER_HOME_PROPERTY.getName())
		       : savedPreviousDir;
	}
}
