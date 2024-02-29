package alekseybykov.pets.mg.core.coreconsts;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author bykov.alexey
 * @since 28.12.2022
 */
@Getter
@RequiredArgsConstructor
public enum Timeouts {

	GUI_PROGRESSBAR_TIMEOUT(800);

	private final int value;
}
