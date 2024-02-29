package alekseybykov.pets.mg.services.paginator.sqllog;

import alekseybykov.pets.mg.core.businessobjects.PageableData;
import alekseybykov.pets.mg.dao.paginator.sqllog.SqlLogPaginatorDao;
import alekseybykov.pets.mg.services.paginator.PageableDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author bykov.alexey
 * @since 26.06.2022
 */
@Service
@Qualifier("SqlLogPaginatorService")
public class SqlLogPaginatorServiceImpl implements PageableDataService {

	@Autowired
	private SqlLogPaginatorDao dao;

	@Override
	public List<PageableData> findRowsRange(int startRownum, int endRownum) {
		return dao.fetchRowsRange(startRownum, endRownum);
	}

	@Override
	public int findRowsCount() {
		return dao.fetchRowsCount();
	}
}
