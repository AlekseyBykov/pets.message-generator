package alekseybykov.pets.mg.gui.components.listeners.admin.guithemes;

import alekseybykov.pets.mg.gui.components.dialogs.admin.guithemes.GuiThemesViewDialog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author bykov.alexey
 * @since 25.02.2024
 */
@Component
public class GuiThemesItemClickListener implements ActionListener {

	@Autowired
	private GuiThemesViewDialog dialog;

	@Override
	public void actionPerformed(ActionEvent event) {
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}
}
