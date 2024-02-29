package alekseybykov.pets.mg.core.ticket.generators;

import alekseybykov.pets.mg.core.businessobjects.message.OAGISMessage;

import java.io.File;

/**
 * @author bykov.alexey
 * @since 29.02.2024
 */
public interface TicketGenerator {

	File generateTicketAsResponse(OAGISMessage message);
}
