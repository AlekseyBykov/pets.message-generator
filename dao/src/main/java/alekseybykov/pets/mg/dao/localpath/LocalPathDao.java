package alekseybykov.pets.mg.dao.localpath;

import alekseybykov.pets.mg.core.businessobjects.PageableData;

import java.util.List;

public interface LocalPathDao {

	List<PageableData> getAllLocalPaths();

	void updateLocalPath(String localPathName, String localPathNewValue);

	String getLocalPathValue(String localPathName);
}
