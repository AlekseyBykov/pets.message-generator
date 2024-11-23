package alekseybykov.pets.mg.services.guitheme;

import alekseybykov.pets.mg.core.businessobjects.PageableData;
import alekseybykov.pets.mg.core.businessobjects.pageable.GuiTheme;

import java.util.List;

public interface GuiThemeService {

	List<PageableData> getAllThemes();

	void deselectAnotherTheme(GuiTheme guiTheme);

	void selectTheme(GuiTheme guiTheme);

	String getActiveTheme();
}
