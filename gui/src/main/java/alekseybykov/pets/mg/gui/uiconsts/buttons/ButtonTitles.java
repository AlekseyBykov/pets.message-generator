package alekseybykov.pets.mg.gui.uiconsts.buttons;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ButtonTitles {

	OK_BTN("OK"),
	CANCEL_BTN("Отмена"),
	CLOSE_BTN("Закрыть"),
	REFRESH_BTN("Обновить выборку"),

	CHOOSE_OAGIS_BTN("Выбрать OAGIS"),
	ADD_ATTACHMENT_BTN("Выбрать вложение"),
	START_OAGIS_IMPORTING_BTN("Импортировать"),

	CHOOSE_TFF_BTN("Выбрать ТФФ"),
	START_TFF_EXPORTING_BTN("Экспортировать");

	@Getter
	private final String title;
}
