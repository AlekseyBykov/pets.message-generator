package alekseybykov.pets.mg.gui.uiconsts;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author bykov.alexey
 * @since 15.04.2021
 */
@RequiredArgsConstructor
public enum GUISize {

	APPLICATION_HEIGHT(800),
	APPLICATION_WIDTH(1280),

	ONE_ROW_TABLE_HEIGHT(85),

	MAX_COMMON_WIDTH(2500);

	@Getter
	private final int value;
}
