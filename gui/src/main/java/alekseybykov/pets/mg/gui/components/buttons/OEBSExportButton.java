package alekseybykov.pets.mg.gui.components.buttons;

import alekseybykov.pets.mg.gui.components.helpers.GUIHelper;
import alekseybykov.pets.mg.gui.components.listeners.oebs_export.OEBSExportButtonClickListener;
import alekseybykov.pets.mg.gui.uiconsts.buttons.ButtonTitles;
import alekseybykov.pets.mg.gui.uiconsts.localresources.IconPaths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

@Component
public class OEBSExportButton extends JButton {

	@Autowired
	private OEBSExportButtonClickListener exportButtonClickListener;

	@PostConstruct
	private void postConstruct() {
		setText(ButtonTitles.START_TFF_EXPORTING_BTN.getTitle());
		setIcon(GUIHelper.scaleImageIcon(IconPaths.SEND_XML_BTN_ICON.getPath(), 20, 20));
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		addActionListener(exportButtonClickListener);
	}
}
