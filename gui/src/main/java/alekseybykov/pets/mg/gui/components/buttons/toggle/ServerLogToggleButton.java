package alekseybykov.pets.mg.gui.components.buttons.toggle;

import alekseybykov.pets.mg.gui.components.listeners.serverlog.StartStopLoggerBtnClickListener;
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
public class ServerLogToggleButton extends JToggleButton {

	@Autowired
	private StartStopLoggerBtnClickListener toggleBtnClickListener;

	@PostConstruct
	private void postConstruct() {
		setText("Старт");
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		addItemListener(toggleBtnClickListener);
	}
}
