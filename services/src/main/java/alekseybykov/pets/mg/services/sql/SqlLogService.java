package alekseybykov.pets.mg.services.sql;

import alekseybykov.pets.mg.core.businessobjects.PageableData;

import java.util.List;

public interface SqlLogService {

	PageableData findRowById(int id);

	String findSqlTextById(String id, String charsetName);

	List<PageableData> findAllRowsBySqlSubstring(
			String sqlSubstring,
			int startRownum,
			int endRownum
	);
}
