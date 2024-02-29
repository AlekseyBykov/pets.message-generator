package alekseybykov.pets.mg.gui.components.buttons.choose;

import alekseybykov.pets.mg.gui.components.helpers.GUIHelper;
import alekseybykov.pets.mg.gui.components.listeners.serverlog.ChooseLogFileButtonClickListener;
import alekseybykov.pets.mg.gui.uiconsts.localresources.IconPaths;
import org.apache.commons.lang3.StringUtils;
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
public class ChooseLogFileButton extends JButton {

	@Autowired
	private ChooseLogFileButtonClickListener chooseLogFileButtonClickListener;

	@PostConstruct
	private void postConstruct() {
		setText(StringUtils.EMPTY);
		setToolTipText("Выбрать лог");
		setIcon(GUIHelper.scaleImageIcon(IconPaths.CHOOSE_XML_BTN_ICON.getPath(), 20, 20));
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		addActionListener(chooseLogFileButtonClickListener);
	}
}
