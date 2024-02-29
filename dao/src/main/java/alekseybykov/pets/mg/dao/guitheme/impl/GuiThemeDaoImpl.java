package alekseybykov.pets.mg.dao.guitheme.impl;

import alekseybykov.pets.mg.core.businessobjects.PageableData;
import alekseybykov.pets.mg.core.businessobjects.pageable.GuiTheme;
import alekseybykov.pets.mg.core.db.h2.H2ConnectionManager;
import alekseybykov.pets.mg.dao.guitheme.GuiThemeDao;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import lombok.var;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author bykov.alexey
 * @since 25.02.2024
 */
@Slf4j
@Repository
public class GuiThemeDaoImpl implements GuiThemeDao {

	private static final JdbcTemplate jdbcTemplate = H2ConnectionManager.getInstance().getJdbcTemplate();

	@Transactional
	@Override
	public List<PageableData> getAllThemes() {
		List<PageableData> configs = new ArrayList<>();
		val sqlText = "\nselect active, name from message_generator.gui_themes\n" +
		              "order by name asc\n\n";
		try {
			configs = jdbcTemplate.query(sqlText, new GuiThemeRowMapper());
		} catch (DataAccessException e) {
			log.error("Не удалось загрузить темы из БД H2. SQL запрос: " + sqlText + "  Исключение: ", e);
		}
		return configs;
	}

	@Transactional
	@Override
	public void setActiveTheme(GuiTheme guiTheme) {
		val sqlText = "\nupdate message_generator.gui_themes set active = true\n" +
		              "where lower(name) = lower(?)\n\n";
		try {
			jdbcTemplate.update(sqlText, guiTheme.getName());
		} catch (DataAccessException e) {
			log.error("Не удалось обновить тему в БД H2. SQL запрос: " + sqlText + "  Исключение: ", e);
		}
	}

	@Override
	public String getActiveTheme() {
		var foundName = StringUtils.EMPTY;
		val sqlText = "\nselect name from message_generator.gui_themes\n" +
		              "where active = ?\n\n";
		try {
			foundName = jdbcTemplate.queryForObject(sqlText, new Object[] {true}, String.class);
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
		} catch (DataAccessException e) {
			log.error("Не удалось найти активную тему в БД H2. SQL запрос: " + sqlText + "  Исключение: ", e);
		}
		return foundName;
	}

	@Transactional
	@Override
	public void resetAnotherTheme(GuiTheme guiTheme) {
		val sqlText = "\nupdate message_generator.gui_themes set active = false where lower(name) != lower(?)\n\n";
		try {
			jdbcTemplate.update(sqlText, guiTheme.getName());
		} catch (DataAccessException e) {
			log.error("Не удалось сбросить активную тему в БД H2. SQL запрос: " + sqlText + "  Исключение: ", e);
		}
	}

	private static class GuiThemeRowMapper implements RowMapper<PageableData> {
		@Override
		public PageableData mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
			return GuiTheme.builder()
					.active(resultSet.getBoolean("active"))
					.name(resultSet.getString("name"))
					.build();
		}
	}
}
