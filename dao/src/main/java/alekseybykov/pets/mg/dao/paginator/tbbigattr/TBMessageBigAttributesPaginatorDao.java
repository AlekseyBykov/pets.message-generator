package alekseybykov.pets.mg.dao.paginator.tbbigattr;

import alekseybykov.pets.mg.core.businessobjects.PageableData;

import java.util.List;

public interface TBMessageBigAttributesPaginatorDao {

	List<PageableData> fetchRowsRange(int startRownum, int endRownum);

	int fetchRowsCount();
}
