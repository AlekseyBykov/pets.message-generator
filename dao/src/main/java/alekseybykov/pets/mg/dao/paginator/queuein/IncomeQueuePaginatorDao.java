package alekseybykov.pets.mg.dao.paginator.queuein;

import alekseybykov.pets.mg.core.businessobjects.PageableData;

import java.util.List;

public interface IncomeQueuePaginatorDao {

	List<PageableData> fetchRowsRange(int startRownum, int endRownum);

	int fetchRowsCount();
}
