package alekseybykov.pets.mg.gui.uiconsts.localresources;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author bykov.alexey
 * @since 28.12.2022
 */
@RequiredArgsConstructor
public enum HtmlPaths {

	INSTRUCTION_TEXT("html/information.html"),
	QUICK_HELP_TEXT("html/about.html");

	@Getter
	private final String path;
}
