package alekseybykov.pets.mg.gui.components.menus.popup;

import alekseybykov.pets.mg.gui.components.listeners.menu.RemoveTFFItemClickListener;
import alekseybykov.pets.mg.gui.uiconsts.menus.TablePopupMenuItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;

@Component
public class TFFTablePopupMenu extends JPopupMenu {

	@Autowired
	private RemoveTFFItemClickListener listener;

	@PostConstruct
	private void postConstruct() {
		JMenuItem item = new JMenuItem(TablePopupMenuItem.REMOVE_SELECTED_TFF.getTitle());
		item.addActionListener(listener);
		add(item);
	}
}
