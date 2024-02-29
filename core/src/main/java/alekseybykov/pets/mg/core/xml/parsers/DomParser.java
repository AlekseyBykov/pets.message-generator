package alekseybykov.pets.mg.core.xml.parsers;

import org.w3c.dom.Element;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

/**
 * @author bykov.alexey
 * @since 09.12.2020
 */
public class DomParser {
	public static String parse(String xml) throws Exception {
		InputSource inputSource = new InputSource(new StringReader(xml));
		Element node = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputSource).getDocumentElement();
		DOMImplementationRegistry domImplementationRegistry = DOMImplementationRegistry.newInstance();
		DOMImplementationLS domImplementationLS = (DOMImplementationLS) domImplementationRegistry.getDOMImplementation("LS");
		LSSerializer lsSerializer = domImplementationLS.createLSSerializer();
		lsSerializer.getDomConfig().setParameter("format-pretty-print", Boolean.TRUE);
		lsSerializer.getDomConfig().setParameter("xml-declaration", xml.startsWith("<?xml"));
		return lsSerializer.writeToString(node);
	}
}
