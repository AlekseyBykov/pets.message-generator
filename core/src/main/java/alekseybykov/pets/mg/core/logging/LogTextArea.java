package alekseybykov.pets.mg.core.logging;

import alekseybykov.pets.mg.core.coreconsts.ContentTypes;
import lombok.SneakyThrows;
import lombok.val;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;
import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.io.StringReader;

public class LogTextArea extends JEditorPane {

	private static LogTextArea instance;

	public static LogTextArea getInstance() {
		if (instance == null) {
			instance = new LogTextArea();
		}
		return instance;
	}

	private LogTextArea() {
		super();
		setContentType(ContentTypes.TEXT_HTML.getType());
		setEditable(false);
		Font font = new Font("Courier New", Font.PLAIN, 12);
		setFont(font);
	}

	@SneakyThrows
	public void append(String string) {
		Document document = super.getDocument();

		Font font = new Font("Courier New", Font.PLAIN, 12);
		val bodyRule = "body { font-family: " + font.getFamily() + "; " +
		               "font-size: " + font.getSize() + "pt; }";
		((HTMLDocument) super.getDocument()).getStyleSheet().addRule(bodyRule);

		StringReader reader = new StringReader(string);
		EditorKit editorKit = getEditorKit();
		editorKit.read(reader, document, document.getLength());
		setCaretPosition(document.getLength());
	}

	public void clear() {
		setText(StringUtils.EMPTY);
	}
}
