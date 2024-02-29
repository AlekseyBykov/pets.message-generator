package alekseybykov.pets.mg.core.exceptions;

/**
 * @author bykov.alexey
 * @since 29.02.2024
 */
public class TicketTypeException extends RuntimeException {

	public static TicketTypeException unknownType() {
		return new TicketTypeException("Неизвестный тип квитанции.");
	}

	private TicketTypeException(String message) {
		super(message);
	}
}
