package alekseybykov.pets.mg.core.exceptions;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class RowIndexOutOfBoundsException extends RuntimeException {

	public RowIndexOutOfBoundsException(String msg) {
		super(msg);
	}
}
