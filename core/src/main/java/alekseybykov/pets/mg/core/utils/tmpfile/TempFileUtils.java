package alekseybykov.pets.mg.core.utils.tmpfile;

import alekseybykov.pets.mg.core.coreconsts.Folders;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.val;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.UUID;

/**
 * Утилитарный класс, которому делегируется логика работы с временными файлами и каталогами.
 *
 * @author bykov.alexey
 * @since 13.06.2022
 */
@UtilityClass
public class TempFileUtils {

	/**
	 * Метод создает временный файл со случайным UUID-именем (например, 5f1f980afe984bdfb298af9a28595528)
	 * в каталоге temp дистрибутива.
	 *
	 * @return - ссылка на созданный временный каталог.
	 */
	public File createTempFile() {
		val tempFileName = UUID.randomUUID().toString().replaceAll("-", StringUtils.EMPTY);
		return new File(Folders.TEMP_FOLDER.getName() + File.separator + tempFileName);
	}

	/**
	 * Метод создает временный каталог со случайным UUID-именем (например, /5f1f980afe984bdfb298af9a28595528/)
	 * в каталоге temp дистрибутива. Данный каталог необходим, когда, например, вложением является архив
	 * с несколькими файлами - чтобы все файла архива были обработаны в пределах данного конкретного временного каталога.
	 *
	 * @return - ссылка на созданный временный каталог.
	 */
	@SuppressWarnings("ResultOfMethodCallIgnored")
	public File createTempFolder() {
		val tempFolderName = UUID.randomUUID().toString().replaceAll("-", StringUtils.EMPTY);
		val tempFolder = new File(Folders.TEMP_FOLDER.getName() + File.separator + tempFolderName);
		if (!tempFolder.exists()) {
			tempFolder.mkdir();
		}

		return tempFolder;
	}

	/**
	 * Метод удаляет содержимое временного каталога temp.
	 * Вызывается при старте приложения.
	 */
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

		// Файл lock оставляем в temp.
		// С помощью данного файла проверяется, запущено ли приложение в единственном экземпляре.
		if (!StringUtils.equals("lock", file.getName())) {
			file.delete();
		}
	}
}
