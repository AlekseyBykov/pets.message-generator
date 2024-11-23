package alekseybykov.pets.mg.gui.uiconsts.dialogs.stands;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum StandsConfigDialogTitles {

	TITLE("Подключение к стенду"),

	ADD_NEW_CONFIG_BTN("Добавить"),
	EDIT_CONFIG_BTN("Редактировать"),
	REMOVE_CONFIG_BTN("Удалить"),

	NEW_CONFIG_DIALOG_TITLE("Новая конфигурация"),
	EDIT_CONFIG_DIALOG_TITLE("Редактирование конфигурации");

	@Getter
	private final String name;
}
