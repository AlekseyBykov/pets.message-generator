package alekseybykov.pets.mg.core.sax;

import alekseybykov.pets.mg.core.businessobjects.message.OAGISMessage;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.StringReader;

/**
 * @author bykov.alexey
 * @since 26.02.2024
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OAGISMessageHandler extends DefaultHandler implements MessageHandler {

	private OAGISMessage oagisMessage;

	private boolean inApplicationAreaTag;
	private boolean inSenderTag;
	private boolean inSenderLogicalID;
	private boolean inUserAreaTag;
	private boolean inCoreGroupTag;

	private StringBuilder senderLogicalID;

	public static OAGISMessageHandler createDefault() {
		return new OAGISMessageHandler();
	}

	@SneakyThrows
	@Override
	public OAGISMessage handle(String messageAsText) {
		oagisMessage = new OAGISMessage();

		try (StringReader stringReader = new StringReader(messageAsText)) {
			newSaxParser().parse(new InputSource(stringReader), this);
		} catch (SAXException e) {
			//markDescriptorAsInvalid(descriptor);
		}
		return oagisMessage;
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) {
		if (StringUtils.equals(qName, "ApplicationArea")) {
			inApplicationAreaTag = true;
			oagisMessage.buildApplicationArea();
		}

		if (StringUtils.equals(qName, "Sender")) {
			inSenderTag = true;
		}

		if (StringUtils.equals(qName, "LogicalID")) {
			inSenderLogicalID = true;

			if (inApplicationAreaTag && inSenderTag) {
				senderLogicalID = new StringBuilder();
			}
		}

		if (StringUtils.equals(qName, "UserArea")) {
			inUserAreaTag = true;
		}

		if (StringUtils.equals(qName, "Group")) {
			val id = attributes.getValue("ID");
			if (StringUtils.equals("Core", id)) {
				inCoreGroupTag = true;
			}
		}

		if (StringUtils.equals(qName, "Property")) {
			/* Тег <Property name="RecipientLogicalID" value="..."/>
			встречается два раза - в ApplicationArea и OriginalApplicationArea. */
			if (inCoreGroupTag && inUserAreaTag && inApplicationAreaTag) {
				val name = attributes.getValue("name");
				if (StringUtils.equals(name, "RecipientLogicalID")) {
					val value = attributes.getValue("value");
					oagisMessage.getApplicationArea().setRecipientLogicalID(value);
				}
			}
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) {
		if (StringUtils.equals(qName, "LogicalID")) {
			if (inApplicationAreaTag && inSenderTag) {
				oagisMessage.getApplicationArea().setSenderLogicalID(senderLogicalID.toString());
			}
			inSenderLogicalID = false;
		}

		if (StringUtils.equals(qName, "Sender")) {
			inSenderTag = false;
		}

		if (StringUtils.equals(qName, "Group")) {
			inCoreGroupTag = false;
		}

		if (StringUtils.equals(qName, "UserArea")) {
			inUserAreaTag = false;
		}

		if (StringUtils.equals(qName, "ApplicationArea")) {
			inApplicationAreaTag = false;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) {
		if (inApplicationAreaTag && inSenderTag && inSenderLogicalID) {
			SAXEventUtils.processCharactersEvent(senderLogicalID, ch, start, length);
		}
	}
}
