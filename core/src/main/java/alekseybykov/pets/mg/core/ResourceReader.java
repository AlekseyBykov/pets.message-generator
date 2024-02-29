package alekseybykov.pets.mg.core;

import lombok.experimental.UtilityClass;

import java.io.InputStream;

/**
 * @author bykov.alexey
 * @since 29.02.2024
 */
@UtilityClass
public class ResourceReader {

	public InputStream read(String resourcePath) {
		return ResourceReader.class.getResourceAsStream(resourcePath);
	}
}
