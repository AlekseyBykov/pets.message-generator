package alekseybykov.pets.mg.gui.components.panels.queueout;

import alekseybykov.pets.mg.gui.components.blobviewers.OutcomeQueueBlobViewer;
import alekseybykov.pets.mg.gui.uiconsts.borders.BorderTitles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * @author bykov.alexey
 * @since 20.04.2021
 */
@Component
public class OutcomeQueueMessageViewPanel extends JPanel {

	@Autowired
	private OutcomeQueueBlobViewer blobViewer;

	@PostConstruct
	private void postConstruct() {
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(10, 10, 10, 7));

		JScrollPane scrollPane = new JScrollPane(blobViewer);
		scrollPane.setBorder(BorderFactory.createTitledBorder(BorderTitles.OUTCOME_QUEUE_FILE.getTitle()));

		add(scrollPane);
	}
}
