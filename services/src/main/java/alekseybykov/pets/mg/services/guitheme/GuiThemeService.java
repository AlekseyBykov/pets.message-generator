package alekseybykov.pets.mg.services.guitheme;

import alekseybykov.pets.mg.core.businessobjects.PageableData;
import alekseybykov.pets.mg.core.businessobjects.pageable.GuiTheme;

import java.util.List;

/**
 * @author bykov.alexey
 * @since 25.02.2024
 */
public interface GuiThemeService {

	List<PageableData> getAllThemes();

	void deselectAnotherTheme(GuiTheme guiTheme);

	void selectTheme(GuiTheme guiTheme);

	String getActiveTheme();
}
