package alekseybykov.pets.mg.gui.components.panels.tbmessage;

import alekseybykov.pets.mg.gui.components.blobviewers.TBMessageBlobViewer;
import alekseybykov.pets.mg.gui.uiconsts.borders.BorderTitles;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * @author bykov.alexey
 * @since 30.04.2021
 */
@Component
public class OAGISMessageViewPanel extends JPanel {

	@Autowired
	private TBMessageBlobViewer blobViewer;

	@PostConstruct
	private void postConstruct() {
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(10, 10, 10, 7));

		val scrollPane = new JScrollPane(blobViewer);
		scrollPane.setBorder(BorderFactory.createTitledBorder(BorderTitles.TB_MESSAGE_BLOB_CONTENT.getTitle()));

		add(scrollPane);
	}
}
