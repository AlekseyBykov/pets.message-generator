package alekseybykov.pets.mg.core.ticket.generators;

import alekseybykov.pets.mg.core.exceptions.TicketTypeException;
import alekseybykov.pets.mg.core.ticket.TicketType;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author bykov.alexey
 * @since 29.02.2024
 */
public class TicketGeneratorsPool {

	private final Map<TicketType, TicketGenerator> GENERATORS_POOL = new HashMap<TicketType, TicketGenerator>() {{
		put(TicketType.CONFIRM_BOD, new ConfirmBodGenerator());
		// todo добавить остальные
	}};

	public TicketGenerator lookup(TicketType type) {
		return Optional.ofNullable(GENERATORS_POOL.get(type))
				.orElseThrow(TicketTypeException::unknownType);
	}
}
