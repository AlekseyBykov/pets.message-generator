package alekseybykov.pets.mg.gui.components.listeners.admin.localpaths;

import alekseybykov.pets.mg.gui.components.dialogs.admin.localpath.EditLocalPathDialog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author bykov.alexey
 * @since 20.02.2024
 */
@Component
public class EditLocalPathButtonClickListener implements ActionListener {

	@Autowired
	private EditLocalPathDialog dialog;

	@Override
	public void actionPerformed(ActionEvent e) {
		dialog.populateDialogFromTableRow();
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}
}
