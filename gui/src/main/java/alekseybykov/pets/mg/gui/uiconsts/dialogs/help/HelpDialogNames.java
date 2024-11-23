package alekseybykov.pets.mg.gui.uiconsts.dialogs.help;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum HelpDialogNames {

	ABOUT_THE_PROGRAM_TITLE("О программе"),
	HELP_TITLE("Инструкция");

	@Getter
	private final String name;
}
