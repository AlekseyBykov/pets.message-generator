package alekseybykov.pets.mg.gui.components.listeners.buttons;

import lombok.val;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
public abstract class ChooseFileButtonClickListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		processButtonClickEvent();
	}

	protected abstract void processButtonClickEvent();

	protected JFileChooser buildFileChooser() {
		val fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setAcceptAllFileFilterUsed(false);

		return fileChooser;
	}
}
