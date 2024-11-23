package alekseybykov.pets.mg.gui.components.listeners.admin.stands;

import alekseybykov.pets.mg.gui.components.dialogs.admin.stands.EditStandsConfigDialog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
public class EditConfigButtonClickListener implements ActionListener {

	@Autowired
	private EditStandsConfigDialog dialog;

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		dialog.populateDialogFromTableRow();
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}
}
