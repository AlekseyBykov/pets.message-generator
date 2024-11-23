package alekseybykov.pets.mg.core.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

public class ReverseInputStream extends InputStream {

	private final RandomAccessFile raf;
	private final byte[] buffer;

	private long bufferEndPointer;
	private int bufferInnerCursor;

	public ReverseInputStream(
			File file,
			int bufferSize
	) throws IOException {
		raf = new RandomAccessFile(file, "r");
		buffer = new byte[bufferSize];

		bufferEndPointer = raf.length();
		bufferInnerCursor = -1;
	}

	@Override
	public int read() throws IOException {
		if (bufferInnerCursor < 0) {
			fillBuffer();
		}
		if (bufferInnerCursor < 0) {
			return -1;
		}

		return (buffer[bufferInnerCursor--]);
	}

	private void fillBuffer() throws IOException {
		if (bufferEndPointer > 0) {
			long bufferStartPointer = Math.max(0L, bufferEndPointer - buffer.length);
			bufferInnerCursor = (int) (bufferEndPointer - bufferStartPointer);
			readFileChunkToBuffer(bufferStartPointer);
			bufferEndPointer = bufferStartPointer;
		}
	}

	private void readFileChunkToBuffer(long bufferStartPointer) throws IOException {
		raf.seek(bufferStartPointer);
		raf.readFully(buffer, 0, bufferInnerCursor--);
	}

	@Override
	public void close() throws IOException {
		raf.close();
	}
}
