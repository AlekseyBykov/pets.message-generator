package alekseybykov.pets.mg.dao.tbbigattr;

import alekseybykov.pets.mg.core.businessobjects.PageableData;

/**
 * @author bykov.alexey
 * @since 04.07.2021
 */
public interface TBMessageBigAttributesDao {

	PageableData fetchRowById(int id);

	String fetchFileById(int id, String charsetName);
}
