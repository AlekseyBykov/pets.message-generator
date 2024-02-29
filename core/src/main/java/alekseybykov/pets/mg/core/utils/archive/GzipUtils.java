package alekseybykov.pets.mg.core.utils.archive;

import alekseybykov.pets.mg.core.coreconsts.BufferSizes;
import alekseybykov.pets.mg.core.logging.UILogger;
import alekseybykov.pets.mg.core.utils.tmpfile.TempFileUtils;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import lombok.var;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Утилитарный класс, которому делегируется логика работы с gzip-архивами.
 *
 * gzip-архив может содержать только один файл, см. https://en.wikipedia.org/wiki/Gzip
 *
 * @author bykov.alexey
 * @since 16.06.2022
 */
@Slf4j
@UtilityClass
public class GzipUtils {

	private static final UILogger uiLogger = UILogger.getInstance();

	/**
	 * Метод определяет, является ли файл GZIP архивом.
	 * Идентификация GZIP выполняется по первым двум байтам, т.о., в ОЗУ размещается незначительное число байт.
	 *
	 * @param file - файл.
	 * @return - true, если файл является gzip архивом, иначе false.
	 */
	public boolean isGzipArchive(File file) {
		var result = false;
		try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
			var fileHeaderSignature = (raf.read() & 0xff | (raf.read() << 8) & 0xff00);
			result = GZIPInputStream.GZIP_MAGIC == fileHeaderSignature;
		} catch (IOException e) {
			val fileName = file.getName();

			log.error("Не удалось определить формат архива с вложением {}. Исключение: ", fileName, e);
			uiLogger.log("<b><font color=\"#FF1832\">Не удалось определить формат архива с вложением " + fileName + "</font></b><br/>");
		}

		return result;
	}

	/**
	 * Метод распаковывает вложение, представленное gzip-архивом и возвращает ссылку на созданный временный файл.
	 * Поскольку фармат gzip позволяет упаковать только один файл (.gz всегда содержит только один файл),
	 * временный каталог не создается.
	 *
	 * @param gzipFile - zip-архив вложения на диске.
	 * @return - временная копия разархивированного файла.
	 */
	@SneakyThrows
	public File ungzipToTempFile(File gzipFile) {
		File tempCopyOfGzipEntry = TempFileUtils.createTempFile();

		byte[] buffer = new byte[BufferSizes.ONE_KB.getSize()];
		GZIPInputStream gzipInputStream = new GZIPInputStream(new FileInputStream(gzipFile));

		FileOutputStream fileOutputStream = new FileOutputStream(tempCopyOfGzipEntry);
		int consumedBytes;

		while ((consumedBytes = gzipInputStream.read(buffer)) > NumberUtils.INTEGER_ZERO) {
			fileOutputStream.write(buffer, 0, consumedBytes);
		}

		gzipInputStream.close();
		fileOutputStream.close();

		return tempCopyOfGzipEntry;
	}

	/**
	 * Метод упаковывает файл во временный gzip-архив.
	 * Согласно спецификации стандарта gzip, файл может быть только один.
	 *
	 * @param gzipFileName - имя, которое будет присвоено gzip-архиву.
	 * @param file - файл, который необходимо упаковать.
	 * @return - ссылка на упакованный в gzip-архив временный файл.
	 */
	@SneakyThrows
	public File packFileToTempGzip(String gzipFileName, File file) {
		File tempZipFile = new File(gzipFileName);
		FileOutputStream fileOutputStream = new FileOutputStream(tempZipFile);
		GZIPOutputStream gzipOuputStream = new GZIPOutputStream(fileOutputStream);
		FileInputStream fileInputStream = new FileInputStream(file);

		val buffer = new byte[BufferSizes.ONE_KB.getSize()];
		int consumedBytes;
		while((consumedBytes = fileInputStream.read(buffer)) >= NumberUtils.INTEGER_ZERO) {
			gzipOuputStream.write(buffer, 0, consumedBytes);
		}

		fileInputStream.close();
		gzipOuputStream.close();
		fileOutputStream.close();

		return tempZipFile;
	}
}
