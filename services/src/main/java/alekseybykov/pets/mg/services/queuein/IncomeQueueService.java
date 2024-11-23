package alekseybykov.pets.mg.services.queuein;

import alekseybykov.pets.mg.core.businessobjects.PageableData;

public interface IncomeQueueService {

	PageableData findRowById(int id);

	String findFileById(int id, String charsetName);
}
