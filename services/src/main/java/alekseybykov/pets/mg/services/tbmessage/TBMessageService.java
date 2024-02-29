package alekseybykov.pets.mg.services.tbmessage;

import alekseybykov.pets.mg.core.businessobjects.PageableData;

import java.nio.charset.Charset;

/**
 * @author bykov.alexey
 * @since 04.07.2021
 */
public interface TBMessageService {

	PageableData findRowById(int id);

	String findFileById(int id, Charset charset);

	byte[] findFileById(int id);
}
