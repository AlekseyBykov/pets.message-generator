package alekseybykov.pets.mg.gui.uiconsts.dialogs.localpath;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum LocalPathDialogSizes {

	COMMON_DIALOG_HEIGHT(450),
	COMMON_DIALOG_WIDTH(700),

	EDIT_DIALOG_HEIGHT(300),
	EDIT_DIALOG_WIDTH(500);

	@Getter
	private final int value;
}
