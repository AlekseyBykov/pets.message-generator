package alekseybykov.pets.mg.gui.components.panels.serverlog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

@Component
public class ServerLoggerTab extends JPanel {

	@Autowired
	private ServerLoggerToolbar toolbar;
	@Autowired
	private ServerLoggerView loggerView;

	@PostConstruct
	private void postConstruct() {
		setLayout(new BorderLayout());
		JPanel containerPanel = new JPanel(new BorderLayout());

		containerPanel.add(toolbar, BorderLayout.NORTH);
		containerPanel.add(loggerView, BorderLayout.CENTER);

		add(containerPanel);
	}
}
