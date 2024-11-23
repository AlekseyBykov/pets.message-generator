package alekseybykov.pets.mg.services.localpath;

import alekseybykov.pets.mg.core.businessobjects.PageableData;

import java.util.List;

public interface LocalPathService {

	List<PageableData> getAllLocalPaths();

	void updateLocalPath(String localPathName, String localPathNewValue);

	String getLocalPathValue(String localPathName);
}
