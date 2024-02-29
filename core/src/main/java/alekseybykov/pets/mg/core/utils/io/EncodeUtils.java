package alekseybykov.pets.mg.core.utils.io;

import alekseybykov.pets.mg.core.coreconsts.EncodingScheme;
import alekseybykov.pets.mg.core.coreconsts.BufferSizes;
import alekseybykov.pets.mg.core.utils.bytes.ByteUtils;
import alekseybykov.pets.mg.core.utils.tmpfile.TempFileUtils;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.var;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.*;
import java.util.Arrays;
import java.util.Base64;

/**
 * @author bykov.alexey
 * @since 13.06.2022
 */
@UtilityClass
public class EncodeUtils {

	public String encodeByteBuffer(byte[] dataBuffer, EncodingScheme encodingScheme) {
		switch (encodingScheme) {
			case BASE64: {
				return Base64.getEncoder().encodeToString(dataBuffer);
			}

			default: {
				return null;
			}
		}
	}

	public byte[] decodeByteBuffer(byte[] dataBuffer, EncodingScheme encodingScheme) {
		switch (encodingScheme) {
			case BASE64: {
				return Base64.getDecoder().decode(dataBuffer);
			}

			default: {
				return null;
			}
		}
	}

	@SneakyThrows
	public File decodeFile(File encodedFile, EncodingScheme encodingScheme) {
		switch (encodingScheme) {

			case BASE64: {
				File decodedFile = TempFileUtils.createTempFile();

				byte[] encodedDataBuffer = new byte[BufferSizes.THREE_KB.getSize()];
				byte[] decodedDataBuffer = new byte[BufferSizes.THREE_KB.getSize()];

				FileInputStream fileInputStream = new FileInputStream(encodedFile);
				FileOutputStream fileOutputStream = new FileOutputStream(decodedFile);

				var counter = 0;
				byte consumedByte;

				while (NumberUtils.INTEGER_MINUS_ONE != (consumedByte = (byte) fileInputStream.read())) {
					encodedDataBuffer[counter++] = consumedByte;
					if (counter == BufferSizes.THREE_KB.getSize()) {
						decodedDataBuffer = decodeByteBuffer(encodedDataBuffer, EncodingScheme.BASE64);

						fileOutputStream.write(decodedDataBuffer);

						Arrays.fill(encodedDataBuffer, (byte) 0);
						Arrays.fill(decodedDataBuffer, (byte) 0);

						counter = 0;
					}
				}

				if (ArrayUtils.isNotEmpty(encodedDataBuffer)) {
					encodedDataBuffer = ByteUtils.removeEmptyTrailingBytes(encodedDataBuffer);
					decodedDataBuffer = decodeByteBuffer(encodedDataBuffer, EncodingScheme.BASE64);
					fileOutputStream.write(decodedDataBuffer);
				}

				fileOutputStream.close();
				fileInputStream.close();

				return decodedFile;
			}

			default: {
				return null;
			}
		}
	}

	@SneakyThrows
	public File encodeToTempFile(InputStream inputStream) {
		File tempFile = TempFileUtils.createTempFile();
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

		// Поскольку Base64 Encoder кодирует каждые 3 байта в 4 символа.
		// Данный буфер будет находиться в ОЗУ, сбрасываясь по достижении THREE_KB
		// на диск, во временный файл.
		byte[] dataBuffer = new byte[BufferSizes.THREE_KB.getSize()];

		int counter = 0;
		byte consumedByte;

		// inputStream.read() - здесь начинает отрабатывать переопределенный в UUIDFilterInputStream метод read().
		// Сначала для uuidFilteredInputStream, затем docUuidFilteredInputStream.
		while (NumberUtils.INTEGER_MINUS_ONE != (consumedByte = (byte) inputStream.read())) {
			// Буфер в ОЗУ заполняется до размера THREE_KB, затем производится запись во временный файл на диск.
			dataBuffer[counter++] = consumedByte;
			if (counter == BufferSizes.THREE_KB.getSize()) {
				String base64EncodedString = EncodeUtils.encodeByteBuffer(dataBuffer, EncodingScheme.BASE64);
				writer.write(base64EncodedString);

				// Заполняем буфер пустыми байтами после того, как записали из него в файл,
				// поскольку иначе в файле могут появиться искажения.
				Arrays.fill(dataBuffer, (byte) 0);
				counter = 0;
			}
		}

		// Если поток inputStream полностью прочитан, в буфере dataBuffer будут оставаться байты,
		// которые также должны быть записаны во временный файл.
		if (ArrayUtils.isNotEmpty(dataBuffer)) {
			// Т.к. мы зануляем буфер выше, в нем будут "пустые байты" в конце, необходимо удалить их,
			// иначе они будут отображаться в UI, а также не удастся декодировать временный файл.
			dataBuffer = ByteUtils.removeEmptyTrailingBytes(dataBuffer);

			String base64EncodedString = EncodeUtils.encodeByteBuffer(dataBuffer, EncodingScheme.BASE64);
			writer.write(base64EncodedString);
		}

		writer.close();

		return tempFile;
	}
}
