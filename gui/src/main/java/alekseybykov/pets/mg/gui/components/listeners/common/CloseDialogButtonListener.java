package alekseybykov.pets.mg.gui.components.listeners.common;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CloseDialogButtonListener implements ActionListener {

	private final JDialog dialog;

	public CloseDialogButtonListener(JDialog dialog) {
		this.dialog = dialog;
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		dialog.setVisible(false);
		dialog.dispose();
	}
}
