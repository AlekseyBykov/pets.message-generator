package alekseybykov.pets.mg.gui.components.panels.sqllog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

/**
 * @author bykov.alexey
 * @since 24.06.2022
 */
@Component
public class SqlLogBrowserTab extends JPanel {

	@Autowired
	private SqlLogTablePanel tablePanel;
	@Autowired
	private SqlLogViewerPanel viewerPanel;

	@PostConstruct
	private void postConstruct() {
		setLayout(new BorderLayout());

		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tablePanel, viewerPanel);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				splitPane.setOneTouchExpandable(true);
				splitPane.setDividerLocation(.87);
			}
		});

		add(splitPane);
	}
}
