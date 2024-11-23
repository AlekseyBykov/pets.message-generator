package alekseybykov.pets.mg.services.queueout.impl;

import alekseybykov.pets.mg.core.businessobjects.PageableData;
import alekseybykov.pets.mg.dao.queueout.OutcomeQueueDao;
import alekseybykov.pets.mg.services.queueout.OutcomeQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OutcomeQueueServiceImpl implements OutcomeQueueService {

	@Autowired
	private OutcomeQueueDao dao;

	@Override
	public PageableData findRowById(int id) {
		return dao.fetchRowById(id);
	}

	@Override
	public String findFileById(int id, String charsetName) {
		return dao.fetchFileById(id, charsetName);
	}
}
