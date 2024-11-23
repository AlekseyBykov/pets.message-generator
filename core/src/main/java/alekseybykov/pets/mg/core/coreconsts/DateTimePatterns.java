package alekseybykov.pets.mg.core.coreconsts;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum DateTimePatterns {

	DATE_TIME_PATTERN("dd.MM.yyyy HH:mm:ss");

	@Getter
	private final String pattern;
}
