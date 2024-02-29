package alekseybykov.pets.mg.core.sax;

import lombok.experimental.UtilityClass;

/**
 * @author bykov.alexey
 * @since 06.11.2022
 */
@UtilityClass
public class SAXEventUtils {

	public void processCharactersEvent(StringBuilder buffer, char[] ch, int start, int length) {
		for (int i = start; i < start + length; i++) {
			buffer.append(clearSpecialChars(ch, i));
		}
	}

	private char clearSpecialChars(char[] ch, int i) {
		return ch[i] == '\n' || ch[i] == '\r' || ch[i] == ' ' || ch[i] == '\t' ? Character.MIN_VALUE : ch[i];
	}
}
