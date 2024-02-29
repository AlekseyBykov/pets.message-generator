package alekseybykov.pets.mg.dao.paginator.queueout;

import alekseybykov.pets.mg.core.businessobjects.PageableData;

import java.util.List;

/**
 * @author bykov.alexey
 * @since 04.07.2021
 */
public interface OutcomeQueuePaginatorDao {

	List<PageableData> fetchRowsRange(int startRownum, int endRownum);

	int fetchRowsCount();
}
