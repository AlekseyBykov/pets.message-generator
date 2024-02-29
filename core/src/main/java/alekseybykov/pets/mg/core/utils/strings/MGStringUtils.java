package alekseybykov.pets.mg.core.utils.strings;

import org.apache.commons.lang3.StringUtils;

/**
 * @author bykov.alexey
 * @since 18.05.2021
 */
public class MGStringUtils {

	public static String splitStringBySeparator(String string, String separator, int part) {
		String[] result = string.split(separator);
		if (result.length != 2) {
			return StringUtils.EMPTY;
		}

		return string.split(separator)[part];
	}

	public static boolean matchCyrillic(String string) {
		for (int i = 0; i < string.length(); i++) {
			if (Character.UnicodeBlock.of(string.charAt(i)).equals(Character.UnicodeBlock.CYRILLIC)) {
				return true;
			}
		}
		return false;
	}
}
