package alekseybykov.pets.mg.core.utils.io;

import alekseybykov.pets.mg.core.businessobjects.file.AttachmentFile;
import alekseybykov.pets.mg.core.coreconsts.EncodingScheme;
import alekseybykov.pets.mg.core.templating.TemplateProcessor;
import alekseybykov.pets.mg.core.coreconsts.Folders;
import alekseybykov.pets.mg.core.utils.archive.GzipUtils;
import alekseybykov.pets.mg.core.utils.archive.ZipUtils;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author bykov.alexey
 * @since 16.06.2022
 */
@Slf4j
@UtilityClass
public class AttachmentProcessor {

	@SneakyThrows
	@SuppressWarnings("UnnecessaryLocalVariable")
	public File processXmlAttachment(AttachmentFile attachment, String attachUUID, String docUUID) {
		File xmlFile = new File(attachment.getPath());
		File base64EncodedFile = TemplateProcessor.process(new FileInputStream(xmlFile), attachUUID, docUUID);

		// Выполняем декодирование байтов из Base64 без предположений об исходной кодировке,
		// тем самым сохраняем ее (неизвестной). Объект объявлен для большей наглядности.
		File base64DecodedFile = EncodeUtils.decodeFile(base64EncodedFile, EncodingScheme.BASE64);

		return base64DecodedFile;
	}

	@SneakyThrows
	public File processZipAttachment(AttachmentFile attachment, String attachUUID, String docUUID) {
		List<File> processedFiles = new ArrayList<>();
		File zipFile = new File(attachment.getPath());

		List<File> tempUnzippedFiles = ZipUtils.getUnzippedTempFiles(zipFile);
		for (File tempUnzippedFile : tempUnzippedFiles) {
			File base64EncodedFile = TemplateProcessor.process(new FileInputStream(tempUnzippedFile), attachUUID, docUUID);
			File base64DecodedFile = EncodeUtils.decodeFile(base64EncodedFile, EncodingScheme.BASE64);

			processedFiles.add(base64DecodedFile);
		}

		val tempFolderPath = Folders.TEMP_FOLDER.getName() + File.separator;

		return ZipUtils.packFilesToTempZip(tempFolderPath + attachment.getName(), processedFiles);
	}

	@SneakyThrows
	public File processGzipAttachment(AttachmentFile attachment, String attachUUID, String docUUID) {
		File gzipFile = new File(attachment.getPath());
		File tempUngzippedFile = GzipUtils.ungzipToTempFile(gzipFile);

		File base64EncodedFile = TemplateProcessor.process(new FileInputStream(tempUngzippedFile), attachUUID, docUUID);
		File base64DecodedFile = EncodeUtils.decodeFile(base64EncodedFile, EncodingScheme.BASE64);

		val tempFolderPath = Folders.TEMP_FOLDER.getName() + File.separator;

		return GzipUtils.packFileToTempGzip(tempFolderPath + attachment.getName(), base64DecodedFile);
	}
}
