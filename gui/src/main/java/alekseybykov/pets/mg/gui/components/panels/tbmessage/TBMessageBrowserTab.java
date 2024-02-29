package alekseybykov.pets.mg.gui.components.panels.tbmessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

/**
 * @author bykov.alexey
 * @since 17.02.2021
 */
@Component
public class TBMessageBrowserTab extends JPanel {

	@Autowired
	private TBMessageTablePanel tbMessageTablePanel;
	@Autowired
	private OAGISMessageViewPanel oagisMessageViewPanel;

	@PostConstruct
	private void postConstruct() {
		setLayout(new BorderLayout());

		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tbMessageTablePanel, oagisMessageViewPanel);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				splitPane.setOneTouchExpandable(true);
				splitPane.setDividerLocation(.87);
			}
		});

		add(splitPane);
	}
}
