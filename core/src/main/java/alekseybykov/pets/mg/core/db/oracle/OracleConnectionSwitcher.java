package alekseybykov.pets.mg.core.db.oracle;

import alekseybykov.pets.mg.core.businessobjects.PageableData;
import alekseybykov.pets.mg.core.businessobjects.pageable.OracleConnection;
import alekseybykov.pets.mg.core.logging.UILogger;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OracleConnectionSwitcher {

	private static final UILogger uiLogger = UILogger.getInstance();

	private static OracleConnectionSwitcher instance;

	private final List<ConnectionSwitchListener> connectionSwitchListeners = new ArrayList<>();

	@Getter
	private List<PageableData> oracleConnections = new ArrayList<>();

	@Getter @Setter
	private String name;
	@Getter @Setter
	private String url;
	@Getter @Setter
	private String driverClassName;
	@Getter @Setter
	private String user;
	@Getter @Setter
	private String password;
	@Getter @Setter
	private boolean active;

	public static OracleConnectionSwitcher getInstance() {
		if (instance == null) {
			instance = new OracleConnectionSwitcher();
		}
		return instance;
	}

	public void resetConnection() {
		name = StringUtils.EMPTY;
		url = StringUtils.EMPTY;
		driverClassName = StringUtils.EMPTY;
		user = StringUtils.EMPTY;
		password = StringUtils.EMPTY;
		active = false;

		notifyObservers(
				OracleConnection.builder()
						.active(active)
						.name(name)
						.url(url)
						.password(password)
						.user(user)
						.driverClassName(driverClassName)
						.build()
		);

		uiLogger.log("Активная конфигурация сброшена<br/>");
		uiLogger.log("Конфигурация не выбрана<br/>");
	}

	public void setActiveConnection(OracleConnection config) {
		this.name = config.getName();
		this.url = config.getUrl();
		this.driverClassName = config.getDriverClassName();
		this.user = config.getUser();
		this.password = config.getPassword();
		this.active = config.isActive();

		notifyObservers(config);

		uiLogger.log("Используется конфигурация из БД H2 [<b>" + this.name + "</b>]<br/>");
	}

	public void deactivateConfigs() {
		for (PageableData oracleConnection : oracleConnections) {
			OracleConnection connection = (OracleConnection) oracleConnection;
			connection.setActive(false);
		}
	}

	public void setConfigs(List<PageableData> oracleConnections) {
		this.oracleConnections = oracleConnections;
		OracleConnection oracleConnection = searchActiveOracleConnection();
		if (oracleConnection != null) {
			setActiveConnection(oracleConnection);
		}
	}

	private OracleConnection searchActiveOracleConnection() {
		for (PageableData oracleConnection : oracleConnections) {
			OracleConnection connection = (OracleConnection) oracleConnection;
			if (connection.isActive()) {
				return connection;
			}
		}
		return null;
	}

	private void notifyObservers(OracleConnection oracleConnection) {
		for (ConnectionSwitchListener connectionSwitchListener : connectionSwitchListeners) {
			connectionSwitchListener.updateCurrentOracleConnection(oracleConnection);
		}
	}

	public void addSwitchListener(ConnectionSwitchListener connectionSwitchListener) {
		connectionSwitchListeners.add(connectionSwitchListener);
	}
}
