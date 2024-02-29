package alekseybykov.pets.mg.gui.uiconsts.menus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author bykov.alexey
 * @since 26.12.2022
 */
@RequiredArgsConstructor
public enum AdminMenuItems {

	TITLE("Администрирование"),

	STANDS_CONFIG("Подключение к стенду..."),
	LOCAL_PATHS_CONFIG("Локальные каталоги..."),
	GUI_THEMES("Темы интерфейса...");

	@Getter
	private final String text;
}
