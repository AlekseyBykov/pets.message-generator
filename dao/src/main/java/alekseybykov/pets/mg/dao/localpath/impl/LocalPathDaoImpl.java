package alekseybykov.pets.mg.dao.localpath.impl;

import alekseybykov.pets.mg.core.businessobjects.PageableData;
import alekseybykov.pets.mg.core.businessobjects.pageable.LocalPath;
import alekseybykov.pets.mg.core.db.h2.H2ConnectionManager;
import alekseybykov.pets.mg.dao.localpath.LocalPathDao;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import lombok.var;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class LocalPathDaoImpl implements LocalPathDao {

	private static final JdbcTemplate jdbcTemplate = H2ConnectionManager.getInstance().getJdbcTemplate();

	@Transactional
	@Override
	public List<PageableData> getAllLocalPaths() {
		List<PageableData> configs = new ArrayList<>();
		val sqlText = "\nselect name, description, path from message_generator.local_paths order by name asc\n\n";

		try {
			configs = jdbcTemplate.query(sqlText, new LocalPathRowMapper());
		} catch (DataAccessException e) {
			log.error("Не удалось загрузить значения локальных путей из БД H2. SQL запрос: " + sqlText + "  Исключение: ", e);
		}
		return configs;
	}

	@Transactional
	@Override
	public void updateLocalPath(
			String localPathName,
			String localPathNewValue
	) {
		val sqlText = "\nupdate message_generator.local_paths set path = ?\n" +
		              "where lower(name) = lower(?)\n\n";
		try {
			jdbcTemplate.update(sqlText, localPathNewValue, localPathName);
		} catch (DataAccessException e) {
			log.error("Не удалось обновить значение " + localPathName +
					" в БД H2. SQL запрос: " + sqlText +
					"  Исключение: ", e);
		}
	}

	@Transactional
	@Override
	public String getLocalPathValue(String localPathName) {
		var localPathValue = StringUtils.EMPTY;
		val sqlText = "\nselect path from message_generator.local_paths where lower(name) = lower(?)\n\n";

		try {
			localPathValue = jdbcTemplate.query(sqlText, new Object[] {localPathName}, new ResultSetExtractor<String>() {
				@Override
				public String extractData(ResultSet rs) throws SQLException,
				                                               DataAccessException {
					return rs.next()
							? rs.getString("path")
							: null;
				}
			});
		} catch (DataAccessException e) {
			log.error("Не удалось получить значение " + localPathName +
					" из БД H2. SQL запрос: " + sqlText +
					"  Исключение: ", e
			);
		}
		return localPathValue;
	}

	private static class LocalPathRowMapper implements RowMapper<PageableData> {
		@Override
		public PageableData mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
			return LocalPath.builder()
					.name(resultSet.getString("name"))
					.description(resultSet.getString("description"))
					.path(resultSet.getString("path"))
					.build();
		}
	}
}
