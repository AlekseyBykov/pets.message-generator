package alekseybykov.pets.mg.core.coreconsts;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BufferSizes {

	ONE_KB((1024), "1 КБ"),

	THREE_KB((ONE_KB.size * 3), "3 КБ"),

	SIX_KB((ONE_KB.size * 6), "6 КБ"),

	/* ------------------------------- */

	ONE_MB((1024 * 1024), "1 МБ"),

	TWO_MB((ONE_MB.size * 2), "2 MБ");

	@Getter
	private final int size;
	@Getter
	private final String name;
}
