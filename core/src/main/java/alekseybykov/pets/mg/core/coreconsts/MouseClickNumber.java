package alekseybykov.pets.mg.core.coreconsts;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author bykov.alexey
 * @since 29.02.2024
 */
@AllArgsConstructor
public enum MouseClickNumber {

	SINGLE_CLICK(1),
	DOUBLE_CLICK(2),
	OUT_OF_RANGE(1);

	public static MouseClickNumber valueOf(int value) {
		return Arrays.stream(values())
				.filter(clickNumber -> clickNumber.value == value)
				.findFirst()
				.orElseThrow(() -> new RuntimeException());
	}

	@Getter
	private int value;
}
