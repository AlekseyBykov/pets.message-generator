package alekseybykov.pets.mg.gui.uiconsts.menus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TablePopupMenuItem {

	REMOVE_SELECTED_TFF("Удалить ТФФ из списка");

	@Getter
	private final String title;
}
