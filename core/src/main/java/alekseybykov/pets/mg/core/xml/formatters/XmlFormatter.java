package alekseybykov.pets.mg.core.xml.formatters;

import alekseybykov.pets.mg.core.xml.parsers.JsoupParser;
import alekseybykov.pets.mg.core.xml.parsers.DomParser;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

public class XmlFormatter {

	@Getter @Setter
	private static boolean wellFormed;

	public static String format(String unformattedXml) {
		unformattedXml = StringUtils.defaultIfEmpty(unformattedXml, StringUtils.EMPTY);
		String formattedXml;
		try {
			formattedXml = DomParser.parse(unformattedXml);
			wellFormed = true;
		} catch (Exception exception) {
			formattedXml = JsoupParser.parse(unformattedXml);
			wellFormed = false;
		}
		return formattedXml;
	}
}
