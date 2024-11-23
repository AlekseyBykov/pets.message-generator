package alekseybykov.pets.mg.dao.h2updater.impl;

import alekseybykov.pets.mg.core.db.h2.H2ConnectionManager;
import alekseybykov.pets.mg.dao.h2updater.H2UpdaterDao;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class H2UpdaterDaoImpl implements H2UpdaterDao {

	private static final JdbcTemplate jdbcTemplate = H2ConnectionManager.getInstance().getJdbcTemplate();

	@Override
	public void applyUpdates(@NotNull String sqlText) {
		try {
			jdbcTemplate.update(sqlText);
		} catch (DataAccessException e) {
			log.error("Не выполнить скрипт " + sqlText + ". Исключение: ", e);
		}
	}
}
