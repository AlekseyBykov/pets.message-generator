package alekseybykov.pets.mg.core.xml.highlighter;

import javax.swing.text.Element;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;

/**
 * @author Kees de Kooter
 * @since 13.01.2006
 */
public class XmlViewFactory extends Object implements ViewFactory {
	/**
	 * @see ViewFactory#create(Element)
	 */
	public View create(Element element) {
		return new XmlView(element);
	}
}
