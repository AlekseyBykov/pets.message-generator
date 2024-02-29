package alekseybykov.pets.mg.core.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

/**
 * Входной поток для побайтного считывания файла в обратном направлении.
 *
 * @author bykov.alexey
 * @since 01.11.2023
 */
public class ReverseInputStream extends InputStream {

	/* Доступ к считываемым данным осуществляется
	  через файл с произвольным доступом. */
	private final RandomAccessFile raf;
	/* Буфер, в который будут считаны данные файла и из которого
	   будут возвращены побайтно в вызывающий метод. */
	private final byte[] buffer;

	/* Указатель на конец отрезка считываемых данных. */
	private long bufferEndPointer;
	/* Длина считываемого отрезка. */
	private int bufferInnerCursor;

	public ReverseInputStream(File file, int bufferSize) throws IOException {
		raf = new RandomAccessFile(file, "r");
		buffer = new byte[bufferSize];

		bufferEndPointer = raf.length();
		bufferInnerCursor = -1;
	}

	/**
	 * Метод возвращает один байт из данного входного потока.
	 * Вызывается в цикле во внешнем методе, т.о. побайтно считывает данные потока.
	 *
	 * @return
	 * @throws IOException
	 */
	@Override
	public int read() throws IOException {
		/* Изначально bufferInnerCursor = -1, поэтому выполняется считывание
		   части содержимого файла в буфер. */
		if (bufferInnerCursor < 0) {
			fillBuffer();
		}
		/* Если в результате очередного вызова read() значение bufferInnerCursor < 0,
		   значит содержимое буфера полностью возвращено в вызывающий метод,
		   надо снова заполнять буфер. */
		if (bufferInnerCursor < 0) {
			return -1;
		}
		/* Часть содержимого файла считано в буфер buffer, при этом bufferInnerCursor > 0.
		   Поэтому при каждом новом вызове read(), будем попадать сюда и возвращать
		   один байт из буфера, пока bufferInnerCursor не станет < 0. */
		return (buffer[bufferInnerCursor--]);
	}

	private void fillBuffer() throws IOException {
		if (bufferEndPointer > 0) {
			/* От конца файла (если это первое считывание) отсчитываем размер буфера.
			  Т.о. bufferStartPointer будет указывать на начало считывамой части файла,
			  bufferEndPointer - на конец. */
			long bufferStartPointer = Math.max(0L, bufferEndPointer - buffer.length);
			/* bufferInnerCursor будет указывать на начало считывамой части файла, но внутри отрезка
			  "bufferStartPointer....bufferEndPointer". Т.е. сколько байт нужно считать == длине
			  отрезка от bufferStartPointer до bufferEndPointer == bufferInnerCursor. */
			bufferInnerCursor = (int) (bufferEndPointer - bufferStartPointer);
			readFileChunkToBuffer(bufferStartPointer);
			/* После считывания части файла в буфер, перемещаем указатель конца части
			   считывамых данных в начало этой части.  */
			bufferEndPointer = bufferStartPointer;
		}
	}

	/**
	 * Метод выполняет считывание части файла в буфер {@link #buffer}.
	 * Размер считываемого отрезка равен "bufferStartPointer + bufferInnerCursor" байт.
	 *
	 * @param bufferStartPointer
	 * @throws IOException
	 */
	private void readFileChunkToBuffer(long bufferStartPointer) throws IOException {
		/* В файле с произвольным доступом устанавливаем указатель
		   начала части считывамых данных.*/
		raf.seek(bufferStartPointer);
		/* Считываем часть данных файла от позиции, установленной методом seek() выше,
		   размером bufferInnerCursor байт.  */
		raf.readFully(buffer, 0, bufferInnerCursor--);
	}

	@Override
	public void close() throws IOException {
		raf.close();
	}
}
