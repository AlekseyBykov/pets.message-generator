package alekseybykov.pets.mg.gui.components.buttons;

import alekseybykov.pets.mg.gui.components.helpers.GUIHelper;
import alekseybykov.pets.mg.gui.components.listeners.oebsimport.OEBSImportButtonClickListener;
import alekseybykov.pets.mg.gui.uiconsts.buttons.ButtonTitles;
import alekseybykov.pets.mg.gui.uiconsts.localresources.IconPaths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

/**
 * @author bykov.alexey
 * @since 27.02.2024
 */
@Component
public class OEBSImportButton extends JButton {

	@Autowired
	private OEBSImportButtonClickListener oebsImportButtonClickListener;

	@PostConstruct
	private void postConstruct() {
		setText(ButtonTitles.START_OAGIS_IMPORTING_BTN.getTitle());
		setIcon(GUIHelper.scaleImageIcon(IconPaths.SEND_XML_BTN_ICON.getPath(), 20, 20));
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		addActionListener(oebsImportButtonClickListener);
	}
}
