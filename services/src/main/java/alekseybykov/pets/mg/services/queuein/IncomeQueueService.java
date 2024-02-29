package alekseybykov.pets.mg.services.queuein;

import alekseybykov.pets.mg.core.businessobjects.PageableData;

/**
 * @author bykov.alexey
 * @since 04.07.2021
 */
public interface IncomeQueueService {

	PageableData findRowById(int id);

	String findFileById(int id, String charsetName);
}
