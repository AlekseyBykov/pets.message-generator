package alekseybykov.pets.mg.dao.queuein;

import alekseybykov.pets.mg.core.businessobjects.PageableData;

public interface IncomeQueueDao {

	PageableData fetchRowById(int id);

	String fetchFileById(int id, String charsetName);
}
