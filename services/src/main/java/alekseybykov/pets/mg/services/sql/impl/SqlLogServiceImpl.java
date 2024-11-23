package alekseybykov.pets.mg.services.sql.impl;

import alekseybykov.pets.mg.core.businessobjects.PageableData;
import alekseybykov.pets.mg.dao.sqllog.SqlLogDao;
import alekseybykov.pets.mg.services.sql.SqlLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SqlLogServiceImpl implements SqlLogService {

	@Autowired
	private SqlLogDao dao;

	@Override
	public PageableData findRowById(int id) {
		return dao.fetchRowById(id);
	}

	@Override
	public String findSqlTextById(String id, String charsetName) {
		return dao.fetchSqlTextById(id, charsetName);
	}

	@Override
	public List<PageableData> findAllRowsBySqlSubstring(
			String sqlSubstring,
			int startRownum,
			int endRownum
	) {
		return dao.findAllRowsBySqlSubstring(sqlSubstring, startRownum, endRownum);
	}
}
