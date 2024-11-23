package alekseybykov.pets.mg.dao.paginator.sqllog;

import alekseybykov.pets.mg.core.businessobjects.PageableData;

import java.util.List;

public interface SqlLogPaginatorDao {

	List<PageableData> fetchRowsRange(int startRownum, int endRownum);

	int fetchRowsCount();
}
