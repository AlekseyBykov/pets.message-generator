package alekseybykov.pets.mg.core.ticket.generators;

import alekseybykov.pets.mg.core.businessobjects.message.OAGISMessage;

import java.io.File;

public interface TicketGenerator {

	File generateTicketAsResponse(OAGISMessage message);
}
