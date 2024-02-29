package alekseybykov.pets.mg.dao.paginator.tbbigattr;

import alekseybykov.pets.mg.core.businessobjects.PageableData;

import java.util.List;

/**
 * @author bykov.alexey
 * @since 04.07.2021
 */
public interface TBMessageBigAttributesPaginatorDao {

	List<PageableData> fetchRowsRange(int startRownum, int endRownum);

	int fetchRowsCount();
}
