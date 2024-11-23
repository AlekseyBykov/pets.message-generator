package alekseybykov.pets.mg.gui.uiconsts.localresources;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum HtmlPaths {

	INSTRUCTION_TEXT("html/information.html"),
	QUICK_HELP_TEXT("html/about.html");

	@Getter
	private final String path;
}
