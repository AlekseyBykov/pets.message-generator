package alekseybykov.pets.mg.core.models.logtoggle;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author bykov.alexey
 * @since 28.02.2024
 */
@RequiredArgsConstructor
public enum LogToggleOptions {

	DETAILED("Подробный"),
	MINIMAL("Минимальный");

	@Getter
	private final String label;
}
