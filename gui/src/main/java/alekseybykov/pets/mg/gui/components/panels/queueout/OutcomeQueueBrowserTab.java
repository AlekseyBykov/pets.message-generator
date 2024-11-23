package alekseybykov.pets.mg.gui.components.panels.queueout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

@Component
public class OutcomeQueueBrowserTab extends JPanel {

	@Autowired
	private OutcomeQueueTablePanel tablePanel;
	@Autowired
	private OutcomeQueueMessageViewPanel messageViewPanel;

	@PostConstruct
	private void postConstruct() {
		setLayout(new BorderLayout());

		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tablePanel, messageViewPanel);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				splitPane.setOneTouchExpandable(true);
				splitPane.setDividerLocation(.87);
			}
		});

		add(splitPane);
	}
}
