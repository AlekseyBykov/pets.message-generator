package alekseybykov.pets.mg.gui.uiconsts.menus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author bykov.alexey
 * @since 26.12.2022
 */
@RequiredArgsConstructor
public enum HelpMenuItems {

	TITLE("Справка"),

	INSTRUCTION_ITEM("Инструкция"),
	QUICK_HELP_ITEM("О программе...");

	@Getter
	private final String text;
}
