package alekseybykov.pets.mg.core.ticket;

import alekseybykov.pets.mg.core.businessobjects.message.OAGISMessage;
import alekseybykov.pets.mg.core.ticket.generators.TicketGeneratorsPool;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class TicketFactory {

	private final TicketGeneratorsPool ticketGenerators = new TicketGeneratorsPool();

	@SneakyThrows
	public File generateTicketAsResponse(TicketType ticketType, OAGISMessage message) {
		return ticketGenerators.lookup(ticketType).generateTicketAsResponse(message);
	}
}
