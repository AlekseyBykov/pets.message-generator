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

/**
 * Класс выполняет побайтное считывание файла в обратном направлении.
 * Возможно, более оптимальное решение в сравнении с {@link ReverseStringsReader},
 * т.к. не используются коллекции Java Collection и манипуляции со строками.
 *
 * @author bykov.alexey
 * @since 31.10.2023
 */
@Component
@Qualifier("ReverseBytesReader")
public class ReverseBytesReader implements ReverseReader {

	private static final int PAGE_SIZE = BufferSizes.SIX_KB.getSize();

	@SneakyThrows
	@Override
	public String read(@NotNull String filePath, @NotNull String encoding) {
		// Содержимое файла считывается буферами размером 256 байт.
		try (
			InputStream reverseFileInputStream = new ReverseInputStream(new File(filePath), 256)) {
			// Сколько всего надо считать байт из файла.
			var buffer = new byte[PAGE_SIZE];

			var counter = 0;
			byte consumedByte;
			/* Считываем содержимое файла, пока не вышли либо за пределы файла,
			   либо за пределы размера считываемой страницы. Используя ReverseInputStream
		       можно считать весь файл с конца до начала, но нам нужно только PAGE_SIZE байт с конца. */
			while (NumberUtils.INTEGER_MINUS_ONE != (consumedByte = (byte) reverseFileInputStream.read())
			       && counter != PAGE_SIZE) {
				buffer[counter++] = consumedByte;
			}

		    /* Если размер файла менее PAGE_SIZE, в буфере будут находиться "пустые байты" в конце,
		      необходимо удалить их, иначе они будут отображаться в UI (пустые квадраты и пр). */
			byte[] packedBuffer = ByteUtils.removeEmptyTrailingBytes(buffer);

		    /* Чтобы отобразить считанную часть файла в прямом читабельном направлении,
		      необходимо перевернуть буфер. */
			ByteUtils.reverseBuffer(packedBuffer);
			// Преобразуем байты в строку, в той или иной кодировке.
			return new String(packedBuffer, encoding);
		}
	}
}
