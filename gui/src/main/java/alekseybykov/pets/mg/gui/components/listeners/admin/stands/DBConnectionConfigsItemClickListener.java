package alekseybykov.pets.mg.gui.components.listeners.admin.stands;

import alekseybykov.pets.mg.gui.components.dialogs.admin.stands.StandsConfigDialog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
public class DBConnectionConfigsItemClickListener implements ActionListener {

	@Autowired
	private StandsConfigDialog dialog;

	@Override
	public void actionPerformed(ActionEvent event) {
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}
}
