package alekseybykov.pets.mg.services.filefolder.impl;

import alekseybykov.pets.mg.core.businessobjects.TransportFolder;
import alekseybykov.pets.mg.core.coreconsts.BufferSizes;
import alekseybykov.pets.mg.core.logging.UILogger;
import alekseybykov.pets.mg.services.filefolder.FileFolderService;
import lombok.SneakyThrows;
import lombok.val;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class FileFolderServiceImpl implements FileFolderService {

	private static final UILogger uiLogger = UILogger.getInstance();

	@NotNull
	@Override
	public List<TransportFolder> findAllSubFolders(@NotNull String rootPath) {
		File[] foundFolders = findFoldersWithRoot(rootPath);
		if (foundFolders == null || ArrayUtils.isEmpty(foundFolders)) {
			return Collections.emptyList();
		}

		return map(foundFolders);
	}

	@Override
	public void sendFilesToTransportFolder(
			Map<String, File> files,
			TransportFolder transportFolder
	) {
		for (Map.Entry<String, File> entry : files.entrySet()) {
			String fileName = entry.getKey();
			File file = entry.getValue();

			uiLogger.log("Документ = [<b>" + fileName + "</b>] будет отправлен в транспортный каталог [<b>" +
			                     transportFolder.getName() + "</b>]<br/>");

			copyFileToFolder(file, fileName, new File(transportFolder.getPath()));

			uiLogger.log("Документ = [<b>" + fileName + "</b>] отправлен <font color=\"#138808\"><b>успешно</b></font>");
		}
	}

	@Nullable
	private File[] findFoldersWithRoot(@NotNull String rootPath) {
		return new File(rootPath).listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.isDirectory();
			}
		});
	}

	@NotNull
	private List<TransportFolder> map(@NotNull File[] folders) {
		List<TransportFolder> result = new ArrayList<>();
		for (File folder : folders) {
			result.add(TransportFolder.builder()
					.name(folder.getName())
					.size(folder.length())
					.path(folder.getAbsolutePath())
					.build());
		}
		return result;
	}

	@SneakyThrows
	private void copyFileToFolder(
			File file,
			String fileName,
			File folder
	) {
		val destination = folder.getAbsolutePath() + "/" + fileName;
		try (InputStream in = new BufferedInputStream(new FileInputStream(file));
		     OutputStream out = new BufferedOutputStream(new FileOutputStream(destination))) {

			byte[] buffer = new byte[BufferSizes.ONE_KB.getSize()];
			int lengthRead;
			while ((lengthRead = in.read(buffer)) > NumberUtils.INTEGER_ZERO) {
				out.write(buffer, 0, lengthRead);
				out.flush();
			}
		}
	}
}
