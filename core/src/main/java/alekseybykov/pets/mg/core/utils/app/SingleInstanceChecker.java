package alekseybykov.pets.mg.core.utils.app;

import alekseybykov.pets.mg.core.coreconsts.ErrorMessages;
import alekseybykov.pets.mg.core.utils.paths.PathUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;

import javax.swing.*;
import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SingleInstanceChecker {

	private static SingleInstanceChecker instance;

	public static SingleInstanceChecker getInstance() {
		if (instance == null) {
			instance = new SingleInstanceChecker();
		}
		return instance;
	}

	@SuppressWarnings("ResultOfMethodCallIgnored")
	public boolean isAlreadyRun() {
		var result = true;
		try {
			File file = new File(PathUtils.getTempFolderPath(), "lock");
			RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
			FileLock fileLock = randomAccessFile.getChannel().tryLock();
			if (fileLock != null) {
				Runtime.getRuntime().addShutdownHook(new Thread() {
					public void run() {
						try {
							fileLock.release();
							randomAccessFile.close();
							file.delete();
						} catch (Exception e) {
							log.error("Не удалось удалить lock-файл.", e);
						}
					}
				});
				result = false;
			} else {
				JOptionPane.showMessageDialog(new JFrame(), ErrorMessages.SINGLE_INSTANCE_CHECKER_ERROR_TEXT.getName(),
						ErrorMessages.SINGLE_INSTANCE_CHECKER_ERROR_TITLE.getName(),
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {
			log.error("Не удалось создать или заблокировать lock-файл.", e);
		}
		return result;
	}
}
