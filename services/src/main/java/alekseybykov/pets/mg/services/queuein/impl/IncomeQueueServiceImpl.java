package alekseybykov.pets.mg.services.queuein.impl;

import alekseybykov.pets.mg.core.businessobjects.PageableData;
import alekseybykov.pets.mg.dao.queuein.IncomeQueueDao;
import alekseybykov.pets.mg.services.queuein.IncomeQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author bykov.alexey
 * @since 16.04.2021
 */
@Service
public class IncomeQueueServiceImpl implements IncomeQueueService {

	@Autowired
	private IncomeQueueDao dao;

	@Override
	public PageableData findRowById(int id) {
		return dao.fetchRowById(id);
	}

	@Override
	public String findFileById(int id, String charsetName) {
		return dao.fetchFileById(id, charsetName);
	}
}
