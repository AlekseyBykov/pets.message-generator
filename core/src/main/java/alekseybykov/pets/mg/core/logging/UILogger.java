package alekseybykov.pets.mg.core.logging;

import alekseybykov.pets.mg.core.coreconsts.DateTimePatterns;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author bykov.alexey
 * @since 22.02.2024
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UILogger {

	private static final SimpleDateFormat sdf = new SimpleDateFormat(DateTimePatterns.DATE_TIME_PATTERN.getPattern());
	private static LogTextArea logTextArea = LogTextArea.getInstance();

	private static UILogger instance;

	public static UILogger getInstance() {
		if (instance == null) {
			logTextArea = LogTextArea.getInstance();
			return new UILogger();
		}
		return instance;
	}

	public void log(String message) {
		logTextArea.append(sdf.format(new Date()) + ": " + message);
	}

	public void clear() {
		logTextArea.clear();
	}
}
