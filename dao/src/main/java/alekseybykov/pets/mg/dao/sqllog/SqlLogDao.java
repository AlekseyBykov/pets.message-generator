package alekseybykov.pets.mg.dao.sqllog;

import alekseybykov.pets.mg.core.businessobjects.PageableData;

import java.util.List;

/**
 * @author bykov.alexey
 * @since 26.06.2022
 */
public interface SqlLogDao {

	PageableData fetchRowById(int id);

	String fetchSqlTextById(String id, String charsetName);

	List<PageableData> findAllRowsBySqlSubstring(String sqlSubstring, int startRownum, int endRownum);
}
