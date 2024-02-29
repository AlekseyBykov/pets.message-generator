package alekseybykov.pets.mg.services.dbconnection.impl;

import alekseybykov.pets.mg.core.businessobjects.PageableData;
import alekseybykov.pets.mg.core.businessobjects.pageable.OracleConnection;
import alekseybykov.pets.mg.dao.dbconnection.DBConnectionDao;
import alekseybykov.pets.mg.services.dbconnection.DBConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author bykov.alexey
 * @since 26.06.2021
 */
@Service
public class DBConnectionServiceImpl implements DBConnectionService {

	@Autowired
	private DBConnectionDao dao;

	@Override
	public void addNewConfig(OracleConnection newConfig) {
		dao.saveNewConfig(newConfig);
	}

	@Override
	public List<PageableData> getAllConfigs() {
		return dao.fetchAllConfigs();
	}

	@Override
	public boolean isConfigDuplicated(OracleConnection newConfig) {
		return dao.isConfigExists(newConfig);
	}

	@Override
	public void updateConfig(OracleConnection config, String oldConfigName) {
		dao.updateConfig(config, oldConfigName);
	}

	@Override
	public void removeConfig(OracleConnection removedConfig) {
		dao.removeConfig(removedConfig);
	}
}
