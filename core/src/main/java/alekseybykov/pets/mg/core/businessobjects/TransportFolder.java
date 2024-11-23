package alekseybykov.pets.mg.core.businessobjects;

import lombok.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class TransportFolder {

	private String name;
	private long size;
	private String path;

	@Override
	public String toString() {
		return name;
	}
}
