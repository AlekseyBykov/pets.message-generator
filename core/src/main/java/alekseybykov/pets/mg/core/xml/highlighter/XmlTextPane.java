package alekseybykov.pets.mg.core.xml.highlighter;

import alekseybykov.pets.mg.core.coreconsts.ContentTypes;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

/**
 * @author Kees de Kooter
 * @since 13.01.2006
 */
@Component
public class XmlTextPane extends JTextPane {

	@PostConstruct
	private void postConstruct() {
		setFont(new Font("Courier New", Font.PLAIN, 12));
		setEditorKitForContentType(ContentTypes.TEXT_XML.getType(), new XmlEditorKit());
		setContentType(ContentTypes.TEXT_XML.getType());
	}
}
