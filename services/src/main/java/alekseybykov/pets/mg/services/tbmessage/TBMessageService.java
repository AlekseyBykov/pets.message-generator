package alekseybykov.pets.mg.services.tbmessage;

import alekseybykov.pets.mg.core.businessobjects.PageableData;

import java.nio.charset.Charset;

public interface TBMessageService {

	PageableData findRowById(int id);

	String findFileById(int id, Charset charset);

	byte[] findFileById(int id);
}
