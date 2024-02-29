package alekseybykov.pets.mg.core.db.oracle;

import alekseybykov.pets.mg.core.businessobjects.pageable.OracleConnection;
import alekseybykov.pets.mg.core.logging.UILogger;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author bykov.alexey
 * @since 01.05.2016
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class OracleConnectionManager implements ConnectionSwitchListener {

	private static final UILogger uiLogger = UILogger.getInstance();

	private BasicDataSource dataSource;
	private Connection connection;
	private JdbcTemplate jdbcTemplate;

	private static OracleConnectionManager instance;

	public static OracleConnectionManager getInstance() {
		if (instance == null) {
			instance = new OracleConnectionManager();
		}
		return instance;
	}

	@Override
	public void updateCurrentOracleConnection(Object object) {
		createPooledDataSource((OracleConnection) object);
		createJdbcTemplate();
	}

	public Connection getConnection() {
		try {
			connection = dataSource.getConnection();
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			log.error("Не удалось подключиться к БД Oracle. Исключение: ", e);

			uiLogger.log("<b><font color=\"#FF1832\">Не удалось подключиться к БД Oracle</font></b><br/>");
			uiLogger.log("<b><font color=\"#FF1832\">Открыто "  + dataSource.getNumActive() + " соединений</font></b><br/>");
		}
		return connection;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void commitChanges() {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.commit();
			}
		} catch (SQLException e) {
			log.error("Не удалось выполнить комит изменений в БД Oracle. Исключение: ", e);
			uiLogger.log("<b><font color=\"#FF1832\">Не удалось выполнить комит изменений в БД Oracle</font></b><br/>");
		}
	}

	public void closeConnection() {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
				connection = null;
			}
		} catch (SQLException e) {
			log.error("Не удалось закрыть соединение с БД Oracle. Исключение: ", e);
			uiLogger.log("<b><font color=\"#FF1832\">Не удалось закрыть соединение с БД Oracle</font></b><br/>");
		}
	}

	/**
	 * Метод устанавливает параметры пула BasicDataSource,
	 * см. https://commons.apache.org/proper/commons-dbcp/configuration.html
	 *
	 * @param config - конфиг, где хранятся настройки соединения (меняются через администрирование).
	 */
	private void createPooledDataSource(OracleConnection config) {
		dataSource = new BasicDataSource();
		dataSource.setDriverClassName(config.getDriverClassName());
		dataSource.setUrl(config.getUrl());
		dataSource.setUsername(config.getUser());
		dataSource.setPassword(config.getPassword());
		// Минимальное число ожидающих коннектов в пуле.
		dataSource.setMinIdle(8);
		// Максимальное число ожидающих коннектов в пуле.
		dataSource.setMaxIdle(15);
		// Коннекты из пула проверяются перед использованием.
		dataSource.setTestOnBorrow(true);
		// Время ожидания доступного коннекта в мс, после чего выбрасывается исключение.
		dataSource.setMaxWait(10000);
		// Запрос, выполняемый при проверке коннекта.
		dataSource.setValidationQuery("select 1 from dual");
		// Не терять коннекты при длительном простое приложения.
		dataSource.setTestWhileIdle(true);
	}

	private void createJdbcTemplate() {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
}
