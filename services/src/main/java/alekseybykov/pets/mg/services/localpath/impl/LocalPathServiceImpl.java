package alekseybykov.pets.mg.services.localpath.impl;

import alekseybykov.pets.mg.core.businessobjects.PageableData;
import alekseybykov.pets.mg.dao.localpath.LocalPathDao;
import alekseybykov.pets.mg.services.localpath.LocalPathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author bykov.alexey
 * @since 19.02.2024
 */
@Service
public class LocalPathServiceImpl implements LocalPathService {

	@Autowired
	private LocalPathDao dao;

	@Override
	public List<PageableData> getAllLocalPaths() {
		return dao.getAllLocalPaths();
	}

	@Override
	public void updateLocalPath(String localPathName, String localPathNewValue) {
		dao.updateLocalPath(localPathName, localPathNewValue);
	}

	@Override
	public String getLocalPathValue(String localPathName) {
		return dao.getLocalPathValue(localPathName);
	}
}
