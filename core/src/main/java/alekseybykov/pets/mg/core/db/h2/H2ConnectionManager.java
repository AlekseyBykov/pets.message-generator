package alekseybykov.pets.mg.core.db.h2;

import alekseybykov.pets.mg.core.utils.paths.PathUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.File;

/**
 * @author bykov.alexey
 * @since 26.06.2021
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class H2ConnectionManager {

	private static final BasicDataSource basicDataSource = new BasicDataSource();
	private static JdbcTemplate jdbcTemplate;

	private static H2ConnectionManager instance;

	public static H2ConnectionManager getInstance() {
		if (instance == null) {
			instance = new H2ConnectionManager();
			createPooledDataSource();
			createJdbcTemplate();
		}
		return instance;
	}

	public static void connect() {
		getInstance();
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	/**
	 * В методе выполняется подключение к БД H2, работающей в embedded mode (локальные JDBC соединения).
	 * Параметр :file определяет file-based persistence mode, при котором все данные сохраняются
	 * в файл MessageGenerator7.1.mv.db в подкаталоге /db.
	 *
	 * Также могут быть сгенерированы файлы
	 * (файлы не следует редактировать, доступ производится через UI):
	 *
	 * - MessageGenerator7.1.lock.db
	 * - MessageGenerator7.1.trace.db
	 * - MessageGenerator7.1.123.temp.db
	 * - MessageGenerator7.1.newFile
	 * - MessageGenerator7.1.oldFile
	 *
	 * В файле /db/init/create-message-generator-db.sql содержится DDL и начальное наполнение таблиц.
	 */
	private static void createPooledDataSource() {
		basicDataSource.setDriverClassName("org.h2.Driver");

		if (isDataBaseAlreadyExists()) {
			basicDataSource.setUrl("jdbc:h2:file:./db/MessageGenerator7.1;");
		} else {
			basicDataSource.setUrl("jdbc:h2:file:./db/MessageGenerator7.1;" +
			                       "INIT=create schema if not exists message_generator\\; " +
			                       "runscript from './db/scripts/init/create-message-generator-db.sql'\\;" +
			                       "set schema message_generator");
		}

		basicDataSource.setUsername("admin");
		basicDataSource.setPassword("admin");
	}

	public static boolean isDataBaseAlreadyExists() {
		return new File(PathUtils.getDBFolderPath() + "/MessageGenerator7.1.mv.db").exists();
	}

	private static void createJdbcTemplate() {
		jdbcTemplate = new JdbcTemplate(basicDataSource);
	}
}
