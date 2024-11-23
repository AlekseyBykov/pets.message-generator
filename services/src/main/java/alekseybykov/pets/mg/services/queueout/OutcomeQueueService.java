package alekseybykov.pets.mg.services.queueout;

import alekseybykov.pets.mg.core.businessobjects.PageableData;

public interface OutcomeQueueService {

	PageableData findRowById(int id);

	String findFileById(int id, String charsetName);
}
