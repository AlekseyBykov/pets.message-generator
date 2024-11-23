package alekseybykov.pets.mg.core.utils.archive;

import alekseybykov.pets.mg.core.coreconsts.ArchiveSignatures;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@Slf4j
@UtilityClass
public class ZipUtils {

	private static final UILogger uiLogger = UILogger.getInstance();

	@SneakyThrows
	private File unzipToTempFolder(String zipFilePath) {
		File unzippedTempFolder = TempFileUtils.createTempFolder();
		byte[] buffer = new byte[BufferSizes.ONE_KB.getSize()];
		ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath));

		ZipEntry zipEntry = zipInputStream.getNextEntry();
		while (zipEntry != null) {
			File copyOfZipEntry = new File(unzippedTempFolder, zipEntry.getName());

			FileOutputStream fileOutputStream = new FileOutputStream(copyOfZipEntry);
			int consumedBytes;

			while ((consumedBytes = zipInputStream.read(buffer)) > NumberUtils.INTEGER_ZERO) {
				fileOutputStream.write(buffer, 0, consumedBytes);
			}

			fileOutputStream.close();

			zipEntry = zipInputStream.getNextEntry();
		}

		zipInputStream.closeEntry();
		zipInputStream.close();

		return unzippedTempFolder;
	}

	@SneakyThrows
	public List<File> getUnzippedTempFiles(File zipFile) {
		List<File> unzippedTempFiles = new ArrayList<>();
		val unzippedTempFolder = unzipToTempFolder(zipFile.getAbsolutePath());
		if (unzippedTempFolder.isDirectory()) {

			File[] unzippedFiles = unzippedTempFolder.listFiles();
			if (unzippedFiles != null) {
				Collections.addAll(unzippedTempFiles, unzippedFiles);
			}
		}

		return unzippedTempFiles;
	}

	@SneakyThrows
	public File packFilesToTempZip(
			String zipFileName,
			List<File> files
	) {
		val tempZipFile = new File(zipFileName);
		val fileOutputStream = new FileOutputStream(tempZipFile);
		val zipOutputStream = new ZipOutputStream(fileOutputStream);

		val buffer = new byte[BufferSizes.ONE_KB.getSize()];

		for (val file : files) {
			val fileInputStream = new FileInputStream(file);
			ZipEntry zipEntry = new ZipEntry(file.getName());
			zipOutputStream.putNextEntry(zipEntry);

			int consumedBytes;
			while((consumedBytes = fileInputStream.read(buffer)) >= NumberUtils.INTEGER_ZERO) {
				zipOutputStream.write(buffer, 0, consumedBytes);
			}

			fileInputStream.close();
		}

		zipOutputStream.close();
		fileOutputStream.close();

		return tempZipFile;
	}

	public boolean isZipArchive(File file) {
		var result = false;
		try (val fis = new FileInputStream(file);
		     val bis = new BufferedInputStream(fis);
		     val dis = new DataInputStream(bis)) {

			int fileHeaderSignature = dis.readInt();
			result = fileHeaderSignature == ArchiveSignatures.ZIP_ARCHIVE_HEADER_BYTES.getValue();
		} catch (IOException e) {
			val fileName = file.getName();
			log.error("Не удалось определить формат архива с вложением {}. Исключение: ", fileName, e);
			uiLogger.log("<b><font color=\"#FF1832\">Не удалось определить формат архива с вложением " + fileName + "</font></b><br/>");
		}

		return result;
	}

	public boolean isZipArchive(byte[] bytes) {
		if (bytes.length < 4) {
			return false;
		}

		int fileHeaderSignature = bytes[0] + (bytes[1] << 8) + (bytes[2] << 16) + (bytes[3] << 24);
		return ArchiveSignatures.ZIP_ARCHIVE_HEADER_BYTES.getValue() == fileHeaderSignature;
	}
}
