package alekseybykov.pets.mg.services.dbconnection;

import alekseybykov.pets.mg.core.businessobjects.PageableData;
import alekseybykov.pets.mg.core.businessobjects.pageable.OracleConnection;

import java.util.List;

public interface DBConnectionService {

	void addNewConfig(OracleConnection newConfig);

	List<PageableData> getAllConfigs();

	boolean isConfigDuplicated(OracleConnection newConfig);

	void updateConfig(OracleConnection config, String oldConfigName);

	void removeConfig(OracleConnection removedConfig);
}
