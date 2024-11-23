package alekseybykov.pets.mg.gui.components;

import alekseybykov.pets.mg.core.db.h2.H2ConnectionManager;
import alekseybykov.pets.mg.core.db.oracle.OracleConnectionManager;
import alekseybykov.pets.mg.core.db.oracle.OracleConnectionSwitcher;
import alekseybykov.pets.mg.core.utils.app.SingleInstanceChecker;
import alekseybykov.pets.mg.core.utils.tmpfile.TempFileUtils;
import alekseybykov.pets.mg.gui.config.ApplicationConfig;
import lombok.SneakyThrows;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.swing.*;

public class MessageGenerator {

	public static void main(String[] args) {
		if (SingleInstanceChecker.getInstance().isAlreadyRun()) {
			System.exit(0);
		}

		init();

		disableExtraBorders();
		SwingUtilities.invokeLater(new Runnable() {
			@SneakyThrows
			public void run() {
				new AnnotationConfigApplicationContext(ApplicationConfig.class).getBean(MainFrame.class);
			}
		});
	}

	private static void disableExtraBorders() {
		UIManager.getDefaults().put(
				"SplitPane.border",
				BorderFactory.createEmptyBorder()
		);
	}

	private static void init() {
		connectH2DataBase();
		initOracleConnectionSwitcher();
		TempFileUtils.clearTempFolder();
	}

	private static void connectH2DataBase() {
		H2ConnectionManager.connect();
	}

	private static void initOracleConnectionSwitcher() {
		OracleConnectionSwitcher.getInstance()
				.addSwitchListener(OracleConnectionManager.getInstance());
	}
}
