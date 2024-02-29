package alekseybykov.pets.mg.core.filereaders.direct;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @author bykov.alexey
 * @since 25.02.2024
 */
public interface DirectReader {

	@NotNull
	String readFile(@NotNull File file, @NotNull Charset charset);

	@NotNull
	String readFile(@NotNull InputStream inputStream, @NotNull Charset charset);
}
