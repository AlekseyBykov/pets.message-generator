package alekseybykov.pets.mg.services.guitheme.impl;

import alekseybykov.pets.mg.core.businessobjects.PageableData;
import alekseybykov.pets.mg.core.businessobjects.pageable.GuiTheme;
import alekseybykov.pets.mg.dao.guitheme.GuiThemeDao;
import alekseybykov.pets.mg.services.guitheme.GuiThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author bykov.alexey
 * @since 25.02.2024
 */
@Service
public class GuiThemeServiceImpl implements GuiThemeService {

	@Autowired
	private GuiThemeDao dao;

	@Override
	public List<PageableData> getAllThemes() {
		return dao.getAllThemes();
	}

	@Override
	public void deselectAnotherTheme(GuiTheme guiTheme) {
		dao.resetAnotherTheme(guiTheme);
	}

	@Override
	public void selectTheme(GuiTheme guiTheme) {
		dao.setActiveTheme(guiTheme);
	}

	@Override
	public String getActiveTheme() {
		return dao.getActiveTheme();
	}
}
