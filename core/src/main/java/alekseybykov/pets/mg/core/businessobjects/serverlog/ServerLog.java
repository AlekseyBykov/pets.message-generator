package alekseybykov.pets.mg.core.businessobjects.serverlog;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bykov.alexey
 * @since 23.10.2023
 */
@Component
public class ServerLog {

	private final List<ServerLogObserver> observers = new ArrayList<>();

	public void addObserver(ServerLogObserver observer) {
		observers.add(observer);
	}

	public void setLogText(String logText) {
		for (ServerLogObserver observer : observers) {
			observer.update(logText);
		}
	}
}
