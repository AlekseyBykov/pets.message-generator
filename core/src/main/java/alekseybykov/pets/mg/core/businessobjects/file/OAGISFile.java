package alekseybykov.pets.mg.core.businessobjects.file;

import alekseybykov.pets.mg.core.businessobjects.message.OAGISMessage;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class OAGISFile {

	private String name;
	private long size;
	private String path;
}
