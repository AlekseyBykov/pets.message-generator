package alekseybykov.pets.mg.core.utils.paths;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.File;

@Slf4j
@UtilityClass
public class PathUtils {

	public static String getScriptsFolderPath() {
		return getJarAbsolutePath() + "/db/scripts/update";
	}

	public static String getDBFolderPath() {
		return getJarAbsolutePath() + "/db";
	}

	public static String getTempFolderPath() {
		return getJarAbsolutePath() + "/temp";
	}

	public static String getLogsFolderPath() {
		return getJarAbsolutePath() + "/logs";
	}

	@SneakyThrows
	private static String getJarAbsolutePath() {
		val jarUri = PathUtils.class.getProtectionDomain()
				.getCodeSource()
				.getLocation()
				.toURI();

		return new File(jarUri).getParentFile().getPath();
	}
}
