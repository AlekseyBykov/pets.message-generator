package alekseybykov.pets.mg.gui.components.panels.queuein;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

/**
 * @author bykov.alexey
 * @since 16.04.2021
 */
@Component
public class IncomeQueueBrowserTab extends JPanel {

	@Autowired
	private IncomeQueueTablePanel tablePanel;
	@Autowired
	private IncomeQueueMessageViewPanel messageViewPanel;

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
