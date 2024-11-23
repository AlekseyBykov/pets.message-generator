package alekseybykov.pets.mg.gui.uiconsts.menus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum HelpMenuItems {

	TITLE("Справка"),

	INSTRUCTION_ITEM("Инструкция"),
	QUICK_HELP_ITEM("О программе...");

	@Getter
	private final String text;
}
