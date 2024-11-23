package alekseybykov.pets.mg.services.paginator;

import alekseybykov.pets.mg.core.businessobjects.PageableData;

import java.util.List;

public interface PageableDataService {

	List<PageableData> findRowsRange(int startRownum, int endRownum);

	int findRowsCount();
}
