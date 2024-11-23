package alekseybykov.pets.mg.gui.components.buttons.refresh;

import alekseybykov.pets.mg.gui.components.helpers.GUIHelper;
import alekseybykov.pets.mg.gui.components.listeners.tbmessage.RefreshTBMessageTableButtonClickListener;
import alekseybykov.pets.mg.gui.uiconsts.buttons.ButtonTitles;
import alekseybykov.pets.mg.gui.uiconsts.localresources.IconPaths;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

@Component
public class RefreshTBMessageTableButton extends JButton {

	@Autowired
	private RefreshTBMessageTableButtonClickListener listener;

	@PostConstruct
	private void postConstruct() {
		setText(StringUtils.EMPTY);
		setToolTipText(ButtonTitles.REFRESH_BTN.getTitle());
		setIcon(GUIHelper.scaleImageIcon(IconPaths.REFRESH_BTN_ICON.getPath(), 20, 20));
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		addActionListener(listener);
	}
}
