package alekseybykov.pets.mg.services.paginator;

import alekseybykov.pets.mg.core.businessobjects.PageableData;

import java.util.List;

/**
 * @author bykov.alexey
 * @since 04.07.2021
 */
public interface PageableDataService {

	List<PageableData> findRowsRange(int startRownum, int endRownum);

	int findRowsCount();
}
