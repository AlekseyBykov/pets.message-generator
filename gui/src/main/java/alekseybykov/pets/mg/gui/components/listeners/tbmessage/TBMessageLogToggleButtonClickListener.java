package alekseybykov.pets.mg.gui.components.listeners.tbmessage;

import lombok.val;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author bykov.alexey
 * @since 28.02.2024
 */
@Component
public class TBMessageLogToggleButtonClickListener implements ItemListener {

	@Override
	public void itemStateChanged(ItemEvent itemEvent) {
		//todo для рефакторинга: источник события можно заинжектить или получить так.
		JToggleButton eventSource = (JToggleButton) (itemEvent.getSource());
		val state = itemEvent.getStateChange();
		if (state == ItemEvent.SELECTED) {
			eventSource.setText("Минимальный лог");
		} else {
			eventSource.setText("Подробный лог");
		}
	}
}
