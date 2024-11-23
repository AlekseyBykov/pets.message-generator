package alekseybykov.pets.mg.core.exceptions;

public class TicketTypeException extends RuntimeException {

	public static TicketTypeException unknownType() {
		return new TicketTypeException("Неизвестный тип квитанции.");
	}

	private TicketTypeException(String message) {
		super(message);
	}
}
