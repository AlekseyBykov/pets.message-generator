package alekseybykov.pets.mg.dao.tbmessage;

import alekseybykov.pets.mg.core.businessobjects.PageableData;

import java.nio.charset.Charset;

public interface TBMessageDao {

	PageableData fetchRowById(int id);

	String fetchFileById(int id, Charset charset);
}
