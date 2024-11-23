package alekseybykov.pets.mg.core.coreconsts;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Timeouts {

	GUI_PROGRESSBAR_TIMEOUT(800);

	private final int value;
}
