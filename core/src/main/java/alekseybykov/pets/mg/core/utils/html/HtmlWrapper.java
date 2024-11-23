package alekseybykov.pets.mg.core.utils.html;

public class HtmlWrapper {

	private static final String wrapperStart = "<html><p style=\"font-weight: normal;font-size: 9px;padding: 5px 5px;\">";
	private static final String wrapperEnd = "</p></html>";

	public static String wrapText(String text) {
		return new StringBuilder()
				.append(wrapperStart)
				.append(text)
				.append(wrapperEnd)
				.toString();
	}
}
