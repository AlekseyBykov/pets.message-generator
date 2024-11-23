package alekseybykov.pets.mg.gui.components.listeners.tbmessage;

import lombok.val;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

@Component
public class TBMessageLogToggleButtonClickListener implements ItemListener {

	@Override
	public void itemStateChanged(ItemEvent itemEvent) {
		// get the event source
		JToggleButton eventSource = (JToggleButton) (itemEvent.getSource());
		val state = itemEvent.getStateChange();
		if (state == ItemEvent.SELECTED) {
			eventSource.setText("Минимальный лог");
		} else {
			eventSource.setText("Подробный лог");
		}
	}
}
