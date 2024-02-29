package alekseybykov.pets.mg.core.ticket.generators;

import alekseybykov.pets.mg.core.ResourceReader;
import alekseybykov.pets.mg.core.businessobjects.message.OAGISMessage;
import alekseybykov.pets.mg.core.templating.TemplateProcessor;
import lombok.val;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author bykov.alexey
 * @since 29.02.2024
 */
public class ConfirmBodGenerator implements TicketGenerator {

	private static final String CONFIRM_BOD_PATH = "/ticket/ConfirmBod.xml";

	@Override
	public File generateTicketAsResponse(OAGISMessage message) {
		InputStream inputStream = ResourceReader.read(CONFIRM_BOD_PATH);

		val sender = message.getApplicationArea().getSenderLogicalID();
		val recipient = message.getApplicationArea().getRecipientLogicalID();

		return TemplateProcessor.processTicket(inputStream, UUID.randomUUID().toString(), sender, recipient);
	}
}
