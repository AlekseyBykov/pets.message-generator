package alekseybykov.pets.mg.core.businessobjects.file;

import alekseybykov.pets.mg.core.coreconsts.ContentTypes;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author bykov.alexey
 * @since 29.04.2016
 */
@Getter
@Setter
@EqualsAndHashCode
public class AttachmentFile {

	private String name;
	private long size;
	private String path;
	private String contentType;

	public AttachmentFile(String name, long size, String path) {
		this.setContentType(ContentTypes.BINARY_UNKNOWN.getType());
		this.setName(name);
		this.setSize(size);
		this.setPath(path);
	}
}
