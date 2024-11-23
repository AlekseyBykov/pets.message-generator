package alekseybykov.pets.mg.gui.components.panels.common;

import alekseybykov.pets.mg.core.models.logtoggle.LogToggleModel;
import alekseybykov.pets.mg.core.models.logtoggle.LogToggleOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
@Scope(value = "prototype")
public class LogToggleView extends JPanel {

	@Autowired
	private LogToggleModel model;

	private final LogOptionListener logOptionListener = new LogOptionListener();

	@PostConstruct
	private void postConstruct() {
		setLayout(new FlowLayout(FlowLayout.LEFT));
		setBorder(BorderFactory.createTitledBorder("Лог"));

		JRadioButton detailed = new JRadioButton(LogToggleOptions.DETAILED.getLabel());
		detailed.setActionCommand(String.valueOf(LogToggleOptions.DETAILED));
		detailed.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		detailed.addActionListener(logOptionListener);
		detailed.setSelected(true);

		JRadioButton minimal = new JRadioButton(LogToggleOptions.MINIMAL.getLabel());
		minimal.setActionCommand(String.valueOf(LogToggleOptions.MINIMAL));
		minimal.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		minimal.addActionListener(logOptionListener);

		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(detailed);
		buttonGroup.add(minimal);

		add(detailed);
		add(minimal);
	}

	private class LogOptionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			JRadioButton rb = (JRadioButton) event.getSource();
			model.setSelectedOption(LogToggleOptions.valueOf(rb.getActionCommand()));
		}
	}
}
