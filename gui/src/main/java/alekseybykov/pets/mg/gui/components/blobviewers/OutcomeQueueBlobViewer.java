package alekseybykov.pets.mg.gui.components.blobviewers;

import alekseybykov.pets.mg.core.coreconsts.ContentTypes;
import alekseybykov.pets.mg.core.xml.highlighter.XmlEditorKit;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

/**
 * @author bykov.alexey
 * @since 27.02.2024
 */
@Component
public class OutcomeQueueBlobViewer extends JTextPane {

	@PostConstruct
	private void postConstruct() {
		setFont(new Font("Courier New", Font.PLAIN, 12));
		setEditorKitForContentType(ContentTypes.TEXT_XML.getType(), new XmlEditorKit());
		setContentType(ContentTypes.TEXT_XML.getType());
	}
}
