package alekseybykov.pets.mg.core.db.oracle;

/**
 * @author bykov.alexey
 * @since 01.07.2021
 */
public interface ConnectionSwitchListener {

	void updateCurrentOracleConnection(Object object);
}
