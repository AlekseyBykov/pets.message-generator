package alekseybykov.pets.mg.gui.components.listeners.admin.localpaths;

import alekseybykov.pets.mg.gui.components.dialogs.admin.localpath.LocalPathsDialog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author bykov.alexey
 * @since 26.12.2022
 */
@Component
public class LocalPathsItemClickListener implements ActionListener {

	@Autowired
	private LocalPathsDialog dialog;

	@Override
	public void actionPerformed(ActionEvent event) {
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}
}
