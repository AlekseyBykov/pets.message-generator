package alekseybykov.pets.mg.services.tbbigattr.impl;

import alekseybykov.pets.mg.core.businessobjects.PageableData;
import alekseybykov.pets.mg.dao.tbbigattr.TBMessageBigAttributesDao;
import alekseybykov.pets.mg.services.tbbigattr.TBMessageBigAttributesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author bykov.alexey
 * @since 15.04.2021
 */
@Service
public class TBMessageBigAttributesServiceImpl implements TBMessageBigAttributesService {

	@Autowired
	private TBMessageBigAttributesDao dao;

	@Override
	public PageableData findRowById(int id) {
		return dao.fetchRowById(id);
	}

	@Override
	public String findFileById(int id, String charsetName) {
		return dao.fetchFileById(id, charsetName);
	}
}
