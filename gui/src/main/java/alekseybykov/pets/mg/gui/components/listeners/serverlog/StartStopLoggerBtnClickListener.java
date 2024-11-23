package alekseybykov.pets.mg.gui.components.listeners.serverlog;

import alekseybykov.pets.mg.core.businessobjects.serverlog.ServerLogReaderSwitch;
import alekseybykov.pets.mg.core.models.complexmodels.ServerToolbarModel;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

@Component
public class StartStopLoggerBtnClickListener implements ItemListener {

	@Autowired
	private ServerLogReaderSwitch readerSwitch;
	@Autowired
	private ServerToolbarModel serverToolbarModel;

	@Override
	public void itemStateChanged(ItemEvent itemEvent) {
		// get the message source
		JToggleButton eventSource = (JToggleButton) (itemEvent.getSource());
		val state = itemEvent.getStateChange();
		if (state == ItemEvent.SELECTED) {
			eventSource.setText("Стоп");
			readerSwitch.setEnabled(true);
		} else {
			eventSource.setText("Старт");
			readerSwitch.setEnabled(false);
		}
	}
}
