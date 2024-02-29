package alekseybykov.pets.mg.gui.uiconsts.dialogs.localpath;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author bykov.alexey
 * @since 20.02.2024
 */
@RequiredArgsConstructor
public enum LocalPathDialogTitles {

	COMMON_DIALOG_TITLE("Локальные каталоги"),
	EDIT_DIALOG_TITLE("Редактирование значения локального пути"),

	EDIT_LOCAL_PATH_BTN("Редактировать");

	@Getter
	private final String name;
}
