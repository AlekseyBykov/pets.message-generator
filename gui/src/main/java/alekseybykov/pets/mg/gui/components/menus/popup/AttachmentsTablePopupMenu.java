package alekseybykov.pets.mg.gui.components.menus.popup;

import alekseybykov.pets.mg.gui.components.listeners.menu.RemoveAttachmentItemClickListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;

@Component
public class AttachmentsTablePopupMenu extends JPopupMenu {

	@Autowired
	private RemoveAttachmentItemClickListener listener;

	@PostConstruct
	private void postConstruct() {
		JMenuItem item = new JMenuItem("Удалить вложение из списка");
		item.addActionListener(listener);
		add(item);
	}
}
