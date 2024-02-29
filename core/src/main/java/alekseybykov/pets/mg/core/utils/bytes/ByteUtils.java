package alekseybykov.pets.mg.core.utils.bytes;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * Утилитарный класс, которому делегируются низкоуровневые байтовые операции.
 *
 * @author bykov.alexey
 * @since 16.06.2022
 */
@Slf4j
@UtilityClass
public class ByteUtils {

	/**
	 * Метод принимает массив байт и возвращает копию, в которой нет пустых (null, 0) байт в конце.
	 *
	 * @param bytes - массив байт, предположительно содержащий в конце бустые байты.
	 * @return - копия исходного массива без пустых байтов в конце.
	 */
	public byte[] removeEmptyTrailingBytes(byte[] bytes) {
		int i = bytes.length - 1;
		while (i >= 0 && bytes[i] == 0) {
			--i;
		}

		return Arrays.copyOf(bytes, i + 1);
	}

	/**
	 * Метод выполняет переворачивание буфера(массива байтов).
	 *
	 * @param buffer - массив с байтами файла.
	 */
	public void reverseBuffer(byte[] buffer) {
		for(int i = buffer.length - 1, j = 0; i > j; i--, j++) {
			byte tmp = buffer[i];
			buffer[i] = buffer[j];
			buffer[j] = tmp;
		}
	}

	public String evaluateFileSize(String size) {
		double length = Double.parseDouble(size);
		if (length < 1024) {
			return length + " B";
		} else {
			length = length / 1024.0;
		}

		if (length < 1024) {
			return Math.round(length * 100) / 100.0 + " KB";
		} else {
			length = length / 1024.0;
		}

		if (length < 1024) {
			return Math.round(length * 100) / 100.0 + " MB";
		} else {
			return Math.round(length / 1024 * 100) / 100.0 + " GB";
		}
	}
}
