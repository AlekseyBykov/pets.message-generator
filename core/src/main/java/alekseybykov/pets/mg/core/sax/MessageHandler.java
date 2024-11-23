package alekseybykov.pets.mg.core.sax;

import alekseybykov.pets.mg.core.businessobjects.message.OAGISMessage;
import lombok.SneakyThrows;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public interface MessageHandler {

	OAGISMessage handle(String messageAsText);

	/*default void markOAGISMessageAsInvalid(OAGISMessage descriptor) {
		descriptor.setInvalid(true);
	}*/

	@SneakyThrows
	default SAXParser newSaxParser() {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setValidating(true);
		return factory.newSAXParser();
	}
}
