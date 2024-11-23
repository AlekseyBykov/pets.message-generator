package alekseybykov.pets.mg.services.tbmessage.impl;

import alekseybykov.pets.mg.core.businessobjects.PageableData;
import alekseybykov.pets.mg.dao.tbmessage.TBMessageDao;
import alekseybykov.pets.mg.services.tbmessage.TBMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;

@Service
public class TBMessageServiceImpl implements TBMessageService {

	@Autowired
	private TBMessageDao dao;

	@Override
	public PageableData findRowById(int id) {
		return dao.fetchRowById(id);
	}

	@Override
	public String findFileById(int id, Charset charset) {
		return dao.fetchFileById(id, charset);
	}

	@Override
	public byte[] findFileById(int id) {
		return new byte[0];
	}
}
