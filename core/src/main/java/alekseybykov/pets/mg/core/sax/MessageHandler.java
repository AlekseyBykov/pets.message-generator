package alekseybykov.pets.mg.core.sax;

import alekseybykov.pets.mg.core.businessobjects.message.OAGISMessage;
import lombok.SneakyThrows;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * @author bykov.alexey
 * @since 21.11.2023
 */
public interface MessageHandler {

	/**
	 * Метод обрабатывает (парсит) файл и формирует из него
	 * объект {@link OAGISMessage}.
	 *
	 * Дескриптр создается не в {@link DefaultHandler#startElement)},
	 * при обнаружении открывающего тега, поскольку достаточно наличия файла.
	 * Т.е. если есть файл, то ему соответствует тот или иной дескриптор,
	 * валидный или нет (пустой, например).
	 *
	 * @param messageAsText - файл.
	 * @return - дескриптор, соответсвующий файлу.
	 */
	OAGISMessage handle(String messageAsText);

	/**
	 * Метод помечает дескриптор как невалидный.
	 *
	 * Например, если файл пустой, будет выброшено {@link org.xml.sax.SAXParseException}.
	 * Аналогично, если в файле есть только символы перевода строки (ds.table),
	 * то он уже не пустой, но и не валидный.
	 */
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
