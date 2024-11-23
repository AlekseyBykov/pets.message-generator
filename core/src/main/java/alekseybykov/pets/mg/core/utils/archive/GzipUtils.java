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

@Slf4j
@UtilityClass
public class GzipUtils {

	private static final UILogger uiLogger = UILogger.getInstance();

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
