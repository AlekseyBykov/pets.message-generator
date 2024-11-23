package alekseybykov.pets.mg.services.tbbigattr;

import alekseybykov.pets.mg.core.businessobjects.PageableData;

public interface TBMessageBigAttributesService {

	PageableData findRowById(int id);

	String findFileById(int id, String charsetName);
}
