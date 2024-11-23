package alekseybykov.pets.mg.dao.paginator.tbmessage;

import alekseybykov.pets.mg.core.businessobjects.PageableData;

import java.util.List;

public interface TBMessagePaginatorDao {

	List<PageableData> fetchRowsRange(int startRownum, int endRownum);

	int fetchRowsCount();
}
