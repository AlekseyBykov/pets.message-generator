package alekseybykov.pets.mg.core.filereaders.direct;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;

public interface DirectReader {

	@NotNull
	String readFile(@NotNull File file, @NotNull Charset charset);

	@NotNull
	String readFile(@NotNull InputStream inputStream, @NotNull Charset charset);
}
