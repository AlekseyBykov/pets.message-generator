package alekseybykov.pets.mg.core.filereaders.reverse;

import alekseybykov.pets.mg.core.coreconsts.BufferSizes;
import alekseybykov.pets.mg.core.io.ReverseInputStream;
import alekseybykov.pets.mg.core.utils.bytes.ByteUtils;
import lombok.SneakyThrows;
import lombok.var;
import org.apache.commons.lang3.math.NumberUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;

@Component
@Qualifier("ReverseBytesReader")
public class ReverseBytesReader implements ReverseReader {

	private static final int PAGE_SIZE = BufferSizes.SIX_KB.getSize();

	@SneakyThrows
	@Override
	public String read(
			@NotNull String filePath,
			@NotNull String encoding
	) {
		try (
			InputStream reverseFileInputStream = new ReverseInputStream(
					new File(filePath),
					256
			)
		) {
			var buffer = new byte[PAGE_SIZE];

			var counter = 0;
			byte consumedByte;
			while (
					-1 != (consumedByte = (byte) reverseFileInputStream.read())
							&& counter != PAGE_SIZE
			) {
				buffer[counter++] = consumedByte;
			}

			byte[] packedBuffer = ByteUtils.removeEmptyTrailingBytes(buffer);
			ByteUtils.reverseBuffer(packedBuffer);

			return new String(packedBuffer, encoding);
		}
	}
}
