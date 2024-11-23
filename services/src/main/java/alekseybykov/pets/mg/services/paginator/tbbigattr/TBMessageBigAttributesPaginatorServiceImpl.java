package alekseybykov.pets.mg.services.paginator.tbbigattr;

import alekseybykov.pets.mg.core.businessobjects.PageableData;
import alekseybykov.pets.mg.dao.paginator.tbbigattr.TBMessageBigAttributesPaginatorDao;
import alekseybykov.pets.mg.services.paginator.PageableDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier("TBMessageBigAttributesPaginatorService")
public class TBMessageBigAttributesPaginatorServiceImpl implements PageableDataService {

	@Autowired
	private TBMessageBigAttributesPaginatorDao dao;

	@Override
	public List<PageableData> findRowsRange(int startRownum, int endRownum) {
		return dao.fetchRowsRange(startRownum, endRownum);
	}

	@Override
	public int findRowsCount() {
		return dao.fetchRowsCount();
	}
}
