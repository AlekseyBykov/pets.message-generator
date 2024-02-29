package alekseybykov.pets.mg.gui.components.buttons.toggle;

import alekseybykov.pets.mg.gui.components.listeners.tbmessage.TBMessageLogToggleButtonClickListener;
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
public class TBMessageLogToggleButton extends JToggleButton {

	@Autowired
	private TBMessageLogToggleButtonClickListener listener;

	@PostConstruct
	private void postConstruct() {
		setText("Подробный лог");
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		addItemListener(listener);
	}
}
