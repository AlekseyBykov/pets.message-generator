package alekseybykov.pets.mg.dao.tbbigattr;

import alekseybykov.pets.mg.core.businessobjects.PageableData;

public interface TBMessageBigAttributesDao {

	PageableData fetchRowById(int id);

	String fetchFileById(int id, String charsetName);
}
