package alekseybykov.pets.mg.gui.components.listeners.help;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author bykov.alexey
 * @since 29.12.2022
 */
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
