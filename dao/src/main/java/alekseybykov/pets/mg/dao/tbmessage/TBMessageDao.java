package alekseybykov.pets.mg.dao.tbmessage;

import alekseybykov.pets.mg.core.businessobjects.PageableData;

import java.nio.charset.Charset;

/**
 * @author bykov.alexey
 * @since 04.07.2021
 */
public interface TBMessageDao {

	PageableData fetchRowById(int id);

	String fetchFileById(int id, Charset charset);
}
