package alekseybykov.pets.mg.gui.components.dialogs.help;

import alekseybykov.pets.mg.core.coreconsts.ContentTypes;
import alekseybykov.pets.mg.core.filereaders.direct.DirectStringsReader;
import alekseybykov.pets.mg.gui.components.helpers.GUIHelper;
import alekseybykov.pets.mg.gui.uiconsts.dialogs.help.HelpDialogNames;
import alekseybykov.pets.mg.gui.uiconsts.localresources.HtmlPaths;
import alekseybykov.pets.mg.gui.uiconsts.localresources.IconPaths;
import lombok.SneakyThrows;
import org.apache.commons.lang3.math.NumberUtils;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.nio.charset.StandardCharsets;

public class AboutDialog extends JDialog {

	private final DirectStringsReader directStringsReader = new DirectStringsReader();

	public AboutDialog() {
		setTitle(HelpDialogNames.ABOUT_THE_PROGRAM_TITLE.getName());
		setModal(true);

		JPanel containerPanel = new JPanel(new BorderLayout());
		containerPanel.add(makeImagePanel(), BorderLayout.NORTH);
		containerPanel.add(makeDescriptionPanel(), BorderLayout.CENTER);

		getContentPane().add(containerPanel);
		setResizable(false);
	}

	private JPanel makeImagePanel() {
		JPanel panel = new JPanel(new BorderLayout());
		//JLabel label = new JLabel(GUIHelper.getImageIconByPath(IconPaths.MSG_GEN_IMAGE.getPath()));
		//panel.add(label);
		return panel;
	}

	private JPanel makeDescriptionPanel() {
		JEditorPane editorPane = new JEditorPane();
		HTMLEditorKit htmlEditorKit = new HTMLEditorKit();
		editorPane.setEditorKit(htmlEditorKit);
		editorPane.setContentType(ContentTypes.TEXT_HTML.getType());
		editorPane.setEditable(false);

		GUIHelper.stylizeHtmlEditorKit(htmlEditorKit);

		Document document = htmlEditorKit.createDefaultDocument();
		editorPane.setDocument(document);
		editorPane.setText(
				directStringsReader.readFile(
						GUIHelper.loadDialogText(HtmlPaths.QUICK_HELP_TEXT.getPath()),
						StandardCharsets.UTF_8)
		);
		editorPane.setCaretPosition(NumberUtils.INTEGER_ZERO);

		editorPane.addHyperlinkListener(new HyperlinkListener() {
			@Override
			@SneakyThrows
			public void hyperlinkUpdate(HyperlinkEvent event) {
				if (HyperlinkEvent.EventType.ACTIVATED.equals(event.getEventType())) {
					Desktop desktop = Desktop.getDesktop();
					desktop.browse(event.getURL().toURI());
				}
			}
		});

		setTitle(HelpDialogNames.ABOUT_THE_PROGRAM_TITLE.getName());

		JScrollPane scrollPane = new JScrollPane(editorPane);
		setSize(new Dimension(560, 600));

		JPanel descriptionPanel = new JPanel(new BorderLayout());
		descriptionPanel.add(scrollPane);
		return descriptionPanel;
	}
}
