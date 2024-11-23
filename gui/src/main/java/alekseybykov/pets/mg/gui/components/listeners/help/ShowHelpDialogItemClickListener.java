package alekseybykov.pets.mg.gui.components.listeners.help;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShowHelpDialogItemClickListener implements ActionListener {

	private final JDialog dialog;

	public ShowHelpDialogItemClickListener(JDialog dialog) {
		this.dialog = dialog;
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}
}
