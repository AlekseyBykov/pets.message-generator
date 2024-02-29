package alekseybykov.pets.mg.core.businessobjects;

import lombok.*;

/**
 * @author bykov.alexey
 * @since 21.02.2024
 */
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class TransportFolder {

	private String name;
	private long size;
	private String path;

	// В комбобоксе нужно отобразить только имя каталога.
	@Override
	public String toString() {
		return name;
	}
}
