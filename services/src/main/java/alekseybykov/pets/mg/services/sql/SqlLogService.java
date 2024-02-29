package alekseybykov.pets.mg.services.sql;

import alekseybykov.pets.mg.core.businessobjects.PageableData;

import java.util.List;

/**
 * @author bykov.alexey
 * @since 26.06.2022
 */
public interface SqlLogService {

	PageableData findRowById(int id);

	String findSqlTextById(String id, String charsetName);

	List<PageableData> findAllRowsBySqlSubstring(String sqlSubstring, int startRownum, int endRownum);
}
