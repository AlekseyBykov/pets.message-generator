package alekseybykov.pets.mg.core.models.logtoggle;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum LogToggleOptions {

	DETAILED("Подробный"),
	MINIMAL("Минимальный");

	@Getter
	private final String label;
}
