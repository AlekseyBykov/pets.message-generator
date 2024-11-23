package alekseybykov.pets.mg.core.utils.tmpfile;

import alekseybykov.pets.mg.core.coreconsts.Folders;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.val;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.UUID;

@UtilityClass
public class TempFileUtils {

	public File createTempFile() {
		val tempFileName = UUID.randomUUID().toString().replaceAll("-", StringUtils.EMPTY);
		return new File(Folders.TEMP_FOLDER.getName() + File.separator + tempFileName);
	}

	@SuppressWarnings("ResultOfMethodCallIgnored")
	public File createTempFolder() {
		val tempFolderName = UUID.randomUUID().toString().replaceAll("-", StringUtils.EMPTY);
		val tempFolder = new File(Folders.TEMP_FOLDER.getName() + File.separator + tempFolderName);
		if (!tempFolder.exists()) {
			tempFolder.mkdir();
		}

		return tempFolder;
	}

	@SneakyThrows
	public void clearTempFolder() {
		clearRecursively(new File(Folders.TEMP_FOLDER.getName()));
	}

	@SneakyThrows
	@SuppressWarnings("ResultOfMethodCallIgnored")
	private void clearRecursively(File file) {
		if (file.isDirectory()) {
			File[] entries = file.listFiles();
			if (entries != null) {
				for (File entry : entries) {
					clearRecursively(entry);
				}
			}
		}

		if (!StringUtils.equals("lock", file.getName())) {
			file.delete();
		}
	}
}
