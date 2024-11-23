package alekseybykov.pets.mg.core;

import lombok.experimental.UtilityClass;

import java.io.InputStream;

@UtilityClass
public class ResourceReader {

	public InputStream read(String resourcePath) {
		return ResourceReader.class.getResourceAsStream(resourcePath);
	}
}
