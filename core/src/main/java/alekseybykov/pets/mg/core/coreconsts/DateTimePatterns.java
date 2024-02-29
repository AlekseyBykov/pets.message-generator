package alekseybykov.pets.mg.core.coreconsts;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author bykov.alexey
 * @since 28.12.2022
 */
@RequiredArgsConstructor
public enum DateTimePatterns {

	DATE_TIME_PATTERN("dd.MM.yyyy HH:mm:ss");

	@Getter
	private final String pattern;
}
