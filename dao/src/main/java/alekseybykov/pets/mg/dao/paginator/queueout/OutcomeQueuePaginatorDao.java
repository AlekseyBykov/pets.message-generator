package alekseybykov.pets.mg.dao.paginator.queueout;

import alekseybykov.pets.mg.core.businessobjects.PageableData;

import java.util.List;

public interface OutcomeQueuePaginatorDao {

	List<PageableData> fetchRowsRange(int startRownum, int endRownum);

	int fetchRowsCount();
}
