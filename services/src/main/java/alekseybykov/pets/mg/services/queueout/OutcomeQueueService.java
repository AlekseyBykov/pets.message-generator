package alekseybykov.pets.mg.services.queueout;

import alekseybykov.pets.mg.core.businessobjects.PageableData;

/**
 * @author bykov.alexey
 * @since 04.07.2021
 */
public interface OutcomeQueueService {

	PageableData findRowById(int id);

	String findFileById(int id, String charsetName);
}
