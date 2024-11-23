package alekseybykov.pets.mg.core.filereaders.reverse;

import org.jetbrains.annotations.NotNull;

public interface ReverseReader {

	String read(
			@NotNull String filePath,
			@NotNull String encoding
	);
}
