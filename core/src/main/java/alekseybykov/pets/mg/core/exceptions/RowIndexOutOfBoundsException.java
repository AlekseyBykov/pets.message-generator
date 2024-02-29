package alekseybykov.pets.mg.core.exceptions;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author bykov.alexey
 * @since 27.06.2021
 */
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class RowIndexOutOfBoundsException extends RuntimeException {

	public RowIndexOutOfBoundsException(String msg) {
		super(msg);
	}
}
