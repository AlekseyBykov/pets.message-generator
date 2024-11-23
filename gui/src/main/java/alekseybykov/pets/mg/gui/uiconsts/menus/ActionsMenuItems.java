package alekseybykov.pets.mg.gui.uiconsts.menus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ActionsMenuItems {

	TITLE("Действия"),

	START_OAGIS_IMPORTING_ITEM("Импортировать OAGIS"),
	CLEAR_ATTACHMENTS("Очистить выбранные xml/вложения"),
	CLEAR_SENDER_LOG("Очистить информацию о процессе генерации");

	@Getter
	private final String text;
}
