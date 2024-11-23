package alekseybykov.pets.mg.dao.queueout;

import alekseybykov.pets.mg.core.businessobjects.PageableData;

public interface OutcomeQueueDao {

	PageableData fetchRowById(int id);

	String fetchFileById(int id, String charsetName);
}
