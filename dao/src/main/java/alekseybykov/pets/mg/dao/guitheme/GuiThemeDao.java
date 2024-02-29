package alekseybykov.pets.mg.dao.guitheme;

import alekseybykov.pets.mg.core.businessobjects.PageableData;
import alekseybykov.pets.mg.core.businessobjects.pageable.GuiTheme;

import java.util.List;

/**
 * @author bykov.alexey
 * @since 25.02.2024
 */
public interface GuiThemeDao {

	List<PageableData> getAllThemes();

	void resetAnotherTheme(GuiTheme guiTheme);

	void setActiveTheme(GuiTheme guiTheme);

	String getActiveTheme();
}
