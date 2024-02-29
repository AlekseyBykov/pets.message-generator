package alekseybykov.pets.mg.gui.components.listeners.buttons;

import alekseybykov.pets.mg.core.businessobjects.file.AttachmentFile;
import alekseybykov.pets.mg.core.coreconsts.ErrorMessages;
import alekseybykov.pets.mg.core.coreconsts.Properties;
import alekseybykov.pets.mg.core.models.table.AttachmentFileListTableModel;
import alekseybykov.pets.mg.services.localpath.LocalPathService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.swing.*;
import java.io.File;

/**
 * @author bykov.alexey
 * @since 29.04.2022
 */
@Component
public class ChooseAttachmentFileButtonClickListener extends ChooseFileButtonClickListener {

	@Autowired
	private AttachmentFileListTableModel attachmentFileListTableModel;
	@Autowired
	private LocalPathService localPathService;

	@Override
	protected void processButtonClickEvent() {
		val fileChooser = buildFileChooser();
		val currentDirectory = findCurrentDirectory();
		fileChooser.setCurrentDirectory(new File(currentDirectory));

		if (fileChooser.showDialog(null, "Выбрать вложение") != JFileChooser.APPROVE_OPTION ) {
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

			localPathService.updateLocalPath("ATTACHMENT_HOME", absolutePath);

			AttachmentFile attachmentFile = new AttachmentFile(fileName, fileChooser.getSelectedFile().length(), absolutePath);
			attachmentFileListTableModel.addAttachmentFile(attachmentFile);
		}
	}

	private String findCurrentDirectory() {
		val savedPreviousDir = localPathService.getLocalPathValue("ATTACHMENT_HOME");
		return StringUtils.isEmpty(savedPreviousDir)
		       ? System.getProperty(Properties.USER_HOME_PROPERTY.getName())
		       : savedPreviousDir;
	}
}
