package alekseybykov.pets.mg.core.filereaders.reverse;

import org.jetbrains.annotations.NotNull;

/**
 * @author bykov.alexey
 * @since 29.10.2023
 */
public interface ReverseReader {

	String read(@NotNull String filePath, @NotNull String encoding);
}
