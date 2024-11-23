package alekseybykov.pets.mg.dao.dbconnection.impl;

import alekseybykov.pets.mg.core.businessobjects.PageableData;
import alekseybykov.pets.mg.core.businessobjects.pageable.OracleConnection;
import alekseybykov.pets.mg.core.db.h2.H2ConnectionManager;
import alekseybykov.pets.mg.dao.dbconnection.DBConnectionDao;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class DBConnectionDaoImpl implements DBConnectionDao {

	private static final JdbcTemplate jdbcTemplate = H2ConnectionManager.getInstance().getJdbcTemplate();

	@Transactional
	@Override
	public void saveNewConfig(OracleConnection newConfig) {
		try {
			if (newConfig.isActive()) {
				resetActiveConfigs();
			}

			GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement statement = connection.prepareStatement(
							"\ninsert into message_generator.db_connections(" +
									"name, url, driverClassName, user, password, active" +
									") values (?, ?, ?, ?, ?, ?)\n\n",
							Statement.RETURN_GENERATED_KEYS);
					statement.setString(1, newConfig.getName());
					statement.setString(2, newConfig.getUrl());
					statement.setString(3, newConfig.getDriverClassName());
					statement.setString(4, newConfig.getUser());
					statement.setString(5, newConfig.getPassword());
					statement.setBoolean(6, newConfig.isActive());
					return statement;
				}
			}, keyHolder);
		} catch (DataAccessException e) {
			log.error("Не удалось сохранить конфигурацию в БД H2. Исключение: ", e);
		}
	}

	@Transactional
	@Override
	public List<PageableData> fetchAllConfigs() {
		List<PageableData> configs = new ArrayList<>();
		val sqlText = "\nselect active, name, url, driverClassName, user, password " +
				"from message_generator.db_connections\n" +
		              "order by name asc\n\n";
		try {
			configs = jdbcTemplate.query(sqlText, new OracleConnectionRowMapper());
		} catch (DataAccessException e) {
			log.error("Не удалось загрузить конфигурации из БД H2. SQL запрос: " + sqlText + "  Исключение: ", e);
		}
		return configs;
	}

	@Transactional
	@Override
	public boolean isConfigExists(OracleConnection newConfig) {
		boolean result = false;
		val sqlText = "\nselect name from message_generator.db_connections\n" +
		              "where name = lower(?)\n" +
		              "and url = lower(?)\n" +
		              "and driverClassName = lower(?)\n" +
		              "and user = lower(?)\n" +
		              "and password = lower(?)\n" +
		              "and active = ?\n\n";
		try {
			val name = jdbcTemplate.queryForObject(sqlText,
					new Object[] {
							newConfig.getName(), newConfig.getUrl(), newConfig.getDriverClassName(),
							newConfig.getUser(), newConfig.getPassword(), newConfig.isActive()
					},
					String.class);
			if (StringUtils.isNotEmpty(name)) {
				result = true;
			}
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
		} catch (DataAccessException e) {
			log.error("Не удалось проверить наличие конфигурации в БД H2. SQL запрос: " + sqlText + "  Исключение: ", e);
		}
		return result;
	}

	@Transactional
	@Override
	public void updateConfig(OracleConnection config, String oldConfigName) {
		val sqlText = "\nupdate message_generator.db_connections set name = ?,\n" +
		              "url = ?,\n" +
		              "driverClassName = ?,\n" +
		              "user = ?,\n" +
		              "password = ?,\n" +
		              "active = ?\n" +
		              "where name = lower(?)\n\n";
		try {
			if (config.isActive()) {
				resetActiveConfigs();
			}

			jdbcTemplate.update(sqlText, config.getName(), config.getUrl(), config.getDriverClassName(),
					config.getUser(), config.getPassword(), config.isActive(), oldConfigName);
		} catch (DataAccessException e) {
			log.error("Не удалось обновить конфигурацию в БД H2. SQL запрос: " + sqlText + "  Исключение: ", e);
		}
	}

	@Transactional
	@Override
	public void removeConfig(OracleConnection removedConfig) {
		val sqlText = "\ndelete from message_generator.db_connections\n" +
		              "where name = lower(?)\n" +
		              "and url = lower(?)\n" +
		              "and driverClassName = lower(?)\n" +
		              "and user = lower(?)\n" +
		              "and password = lower(?)\n" +
		              "and active = ?\n\n";
		try {
			jdbcTemplate.update(sqlText, removedConfig.getName(), removedConfig.getUrl(), removedConfig.getDriverClassName(),
					removedConfig.getUser(), removedConfig.getPassword(), removedConfig.isActive());
		} catch (DataAccessException e) {
			log.error("Не удалось удалить конфигурацию из БД H2. SQL запрос: " + sqlText + "  Исключение: ", e);
		}
	}

	private void resetActiveConfigs() {
		val sqlText = "\nupdate message_generator.db_connections set active = false where active = true\n\n";
		try {
			jdbcTemplate.update(sqlText);
		} catch (DataAccessException e) {
			log.error("Не удалось сбросить активную конфигурацию в БД H2. SQL запрос: " + sqlText + "  Исключение: ", e);
		}
	}

	private static class OracleConnectionRowMapper implements RowMapper<PageableData> {
		@Override
		public PageableData mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
			return OracleConnection.builder()
					.active(resultSet.getBoolean("active"))
					.name(resultSet.getString("name"))
					.driverClassName(resultSet.getString("driverClassName"))
					.url(resultSet.getString("url"))
					.user(resultSet.getString("user"))
					.password(resultSet.getString("password"))
					.build();
		}
	}
}
