package alekseybykov.pets.mg.gui.components.buttons.choose;

import alekseybykov.pets.mg.gui.components.helpers.GUIHelper;
import alekseybykov.pets.mg.gui.components.listeners.buttons.ChooseOAGISFileButtonClickListener;
import alekseybykov.pets.mg.gui.uiconsts.localresources.IconPaths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

/**
 * @author bykov.alexey
 * @since 28.02.2024
 */
@Component
public class ChooseOAGISFileButton extends JButton {

	@Autowired
	private ChooseOAGISFileButtonClickListener chooseOAGISFileButtonClickListener;

	@PostConstruct
	private void postConstruct() {
		setText("Выбрать OAGIS");
		setIcon(GUIHelper.scaleImageIcon(IconPaths.CHOOSE_XML_BTN_ICON.getPath(), 20, 20));
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		addActionListener(chooseOAGISFileButtonClickListener);
	}
}
