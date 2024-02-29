package alekseybykov.pets.mg.services.paginator.queueout;

import alekseybykov.pets.mg.core.businessobjects.PageableData;
import alekseybykov.pets.mg.dao.paginator.queueout.OutcomeQueuePaginatorDao;
import alekseybykov.pets.mg.services.paginator.PageableDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author bykov.alexey
 * @since 04.07.2021
 */
@Service
@Qualifier("OutcomeQueuePaginatorService")
public class OutcomeQueuePaginatorServiceImpl implements PageableDataService {

	@Autowired
	private OutcomeQueuePaginatorDao dao;

	@Override
	public List<PageableData> findRowsRange(int startRownum, int endRownum) {
		return dao.fetchRowsRange(startRownum, endRownum);
	}

	@Override
	public int findRowsCount() {
		return dao.fetchRowsCount();
	}
}
