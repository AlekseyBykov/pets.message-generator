package alekseybykov.pets.mg.dao.localpath;

import alekseybykov.pets.mg.core.businessobjects.PageableData;

import java.util.List;

/**
 * @author bykov.alexey
 * @since 19.02.2024
 */
public interface LocalPathDao {

	List<PageableData> getAllLocalPaths();

	void updateLocalPath(String localPathName, String localPathNewValue);

	String getLocalPathValue(String localPathName);
}
