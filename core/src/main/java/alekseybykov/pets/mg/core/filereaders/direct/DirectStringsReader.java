package alekseybykov.pets.mg.core.filereaders.direct;

import lombok.SneakyThrows;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.Charset;

/**
 * @author bykov.alexey
 * @since 25.02.2024
 */
@Component
public class DirectStringsReader implements DirectReader {

	@SneakyThrows
	@NotNull
	@Override
	public String readFile(@NotNull File file, @NotNull Charset charset) {
		return readFileLineByLine(new FileInputStream(file), charset);
	}

	@NotNull
	@Override
	public String readFile(@NotNull InputStream inputStream, @NotNull Charset charset) {
		return readFileLineByLine(inputStream, charset);
	}

	@SneakyThrows
	@NotNull
	private String readFileLineByLine(@NotNull InputStream inputStream, @NotNull Charset charset) {
		val sb = new StringBuilder();
		try (InputStreamReader isr = new InputStreamReader(inputStream, charset);
		     BufferedReader br = new BufferedReader(isr)) {

			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
		}
		return sb.toString();
	}
}

