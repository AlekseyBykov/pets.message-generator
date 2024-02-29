package alekseybykov.pets.mg.core.businessobjects.pageable;

import alekseybykov.pets.mg.core.businessobjects.PageableData;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author bykov.alexey
 * @since 19.02.2024
 */
@Getter
@Setter
@Builder
public class LocalPath implements PageableData {

	private String name;
	private String description;
	private String path;
}
