package alekseybykov.pets.mg.gui.components.buttons.choose;

import alekseybykov.pets.mg.gui.components.helpers.GUIHelper;
import alekseybykov.pets.mg.gui.components.listeners.buttons.ChooseAttachmentFileButtonClickListener;
import alekseybykov.pets.mg.gui.uiconsts.buttons.ButtonTitles;
import alekseybykov.pets.mg.gui.uiconsts.localresources.IconPaths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

@Component
public class ChooseAttachmentFileButton extends JButton {

	@Autowired
	private ChooseAttachmentFileButtonClickListener listener;

	@PostConstruct
	private void postConstruct() {
		setText(ButtonTitles.ADD_ATTACHMENT_BTN.getTitle());
		setIcon(GUIHelper.scaleImageIcon(IconPaths.CHOOSE_XML_BTN_ICON.getPath(), 20, 20));
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		addActionListener(listener);
	}
}
