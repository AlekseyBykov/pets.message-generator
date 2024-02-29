package alekseybykov.pets.mg.core.xml.parsers;

import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;

/**
 * @author bykov.alexey
 * @since 09.12.2020
 */
public class JsoupParser {
	public static String parse(String xml) {
		val document = Jsoup.parse(xml, StringUtils.EMPTY, Parser.xmlParser());
		document.outputSettings().indentAmount(NumberUtils.INTEGER_ZERO).prettyPrint(false);
		return document.toString();
	}
}
