package alekseybykov.pets.mg.gui.uiconsts.dialogs.stands;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author bykov.alexey
 * @since 26.12.2022
 */
@RequiredArgsConstructor
public enum StandsConfigDialogSizes {

	CURRENT_CONFIG_DIALOG_HEIGHT(450),
	CURRENT_CONFIG_DIALOG_WIDTH(700),

	NEW_CONFIG_DIALOG_HEIGHT(300),
	NEW_CONFIG_DIALOG_WINDOW_SIZE_WIDTH(500),

	EDIT_CONFIG_DIALOG_WINDOW_SIZE_HEIGHT(300),
	EDIT_CONFIG_DIALOG_WINDOW_SIZE_WIDTH(500);

	@Getter
	private final int value;
}
