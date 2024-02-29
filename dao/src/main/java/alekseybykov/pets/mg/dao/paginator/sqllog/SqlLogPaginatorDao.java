package alekseybykov.pets.mg.dao.paginator.sqllog;

import alekseybykov.pets.mg.core.businessobjects.PageableData;

import java.util.List;

/**
 * @author bykov.alexey
 * @since 26.06.2022
 */
public interface SqlLogPaginatorDao {

	List<PageableData> fetchRowsRange(int startRownum, int endRownum);

	int fetchRowsCount();
}
