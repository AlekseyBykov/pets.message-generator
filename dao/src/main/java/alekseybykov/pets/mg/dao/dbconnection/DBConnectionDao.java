package alekseybykov.pets.mg.dao.dbconnection;

import alekseybykov.pets.mg.core.businessobjects.PageableData;
import alekseybykov.pets.mg.core.businessobjects.pageable.OracleConnection;

import java.util.List;

/**
 * @author bykov.alexey
 * @since 19.02.2024
 */
public interface DBConnectionDao {

	void saveNewConfig(OracleConnection newConfig);

	List<PageableData> fetchAllConfigs();

	boolean isConfigExists(OracleConnection newConfig);

	void updateConfig(OracleConnection config, String oldConfigName);

	void removeConfig(OracleConnection removedConfig);
}
