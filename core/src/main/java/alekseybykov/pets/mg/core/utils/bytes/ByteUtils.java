package alekseybykov.pets.mg.core.utils.bytes;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
@UtilityClass
public class ByteUtils {

	public byte[] removeEmptyTrailingBytes(byte[] bytes) {
		int i = bytes.length - 1;
		while (i >= 0 && bytes[i] == 0) {
			--i;
		}

		return Arrays.copyOf(bytes, i + 1);
	}

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
