package alekseybykov.pets.mg.core.businessobjects.file;

import alekseybykov.pets.mg.core.businessobjects.message.OAGISMessage;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * XML файл OAGIS. В отличие от {@link OAGISMessage}, представляет собой
 * сущность ОС, файл на диске.
 *
 * @author bykov.alexey
 * @since 16.02.2024
 */
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class OAGISFile {

	private String name;
	private long size;
	private String path;
}
