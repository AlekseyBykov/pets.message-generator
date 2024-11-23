package alekseybykov.pets.mg.gui.components.panels.tbbigattr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

@Component
public class TBMessageBigAttributesBrowserTab extends JPanel {

	@Autowired
	private TBMessageBigAttributesTablePanel tbMessageBigAttributesTablePanel;
	@Autowired
	private AttachmentViewerPanel attachmentViewerPanel;

	@PostConstruct
	private void postConstruct() {
		setLayout(new BorderLayout());

		JSplitPane splitPane = new JSplitPane(
				JSplitPane.VERTICAL_SPLIT,
				tbMessageBigAttributesTablePanel,
				attachmentViewerPanel
		);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				splitPane.setOneTouchExpandable(true);
				splitPane.setDividerLocation(.87);
			}
		});

		add(splitPane);
	}
}
