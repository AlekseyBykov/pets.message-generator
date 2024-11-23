package alekseybykov.pets.mg.core.utils.io;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

@UtilityClass
public class IOStreamUtils {

	@SneakyThrows
	public ByteArrayInputStream readToByteArrayInputStream(InputStream inputStream) {
		ByteArrayInputStream byteArrayInputStream;
		try (BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream)) {

			byte[] dataBuffer = new byte[inputStream.available()];
			bufferedInputStream.read(dataBuffer);
			byteArrayInputStream = new ByteArrayInputStream(dataBuffer);
		} finally {
			IOUtils.closeQuietly(inputStream);
		}

		return byteArrayInputStream;
	}
}
