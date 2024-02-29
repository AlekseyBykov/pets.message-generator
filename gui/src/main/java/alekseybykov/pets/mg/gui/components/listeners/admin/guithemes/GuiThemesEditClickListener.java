package alekseybykov.pets.mg.gui.components.listeners.admin.guithemes;

import alekseybykov.pets.mg.gui.components.dialogs.admin.guithemes.GuiThemesEditDialog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author bykov.alexey
 * @since 25.02.2024
 */
@Component
public class GuiThemesEditClickListener implements ActionListener {

	@Autowired
	private GuiThemesEditDialog dialog;

	@Override
	public void actionPerformed(ActionEvent e) {
		dialog.populateDialogFromTableRow();
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}
}
