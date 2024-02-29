package alekseybykov.pets.mg.core.businessobjects.serverlog;

/**
 * @author bykov.alexey
 * @since 24.10.2023
 */
public interface ServerLogObserver {

	void update(String logText);
}
