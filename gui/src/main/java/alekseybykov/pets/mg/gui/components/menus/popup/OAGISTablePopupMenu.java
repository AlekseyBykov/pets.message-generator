package alekseybykov.pets.mg.gui.components.menus.popup;

import alekseybykov.pets.mg.gui.components.listeners.menu.RemoveOAGISItemClickListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;

@Component
public class OAGISTablePopupMenu extends JPopupMenu {

	@Autowired
	private RemoveOAGISItemClickListener listener;

	@PostConstruct
	private void postConstruct() {
		JMenuItem item = new JMenuItem("Удалить OAGIS из списка");
		item.addActionListener(listener);
		add(item);
	}
}
