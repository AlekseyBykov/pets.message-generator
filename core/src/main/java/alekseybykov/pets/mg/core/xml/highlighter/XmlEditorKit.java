package alekseybykov.pets.mg.core.xml.highlighter;

import alekseybykov.pets.mg.core.coreconsts.ContentTypes;

import javax.swing.text.StyledEditorKit;
import javax.swing.text.ViewFactory;

/**
 * @author Kees de Kooter
 * @since 13.01.2006
 */
public class XmlEditorKit extends StyledEditorKit {

	private final ViewFactory xmlViewFactory;

	public XmlEditorKit() {
		xmlViewFactory = new XmlViewFactory();
	}

	@Override
	public ViewFactory getViewFactory() {
		return xmlViewFactory;
	}

	@Override
	public String getContentType() {
		return ContentTypes.TEXT_HTML.getType();
	}
}
