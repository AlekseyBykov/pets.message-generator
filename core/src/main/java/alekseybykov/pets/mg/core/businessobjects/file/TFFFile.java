package alekseybykov.pets.mg.core.businessobjects.file;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author bykov.alexey
 * @since 16.02.2024
 */
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class TFFFile {

	private String name;
	private long size;
	private String path;
}
