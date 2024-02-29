package alekseybykov.pets.mg.gui.components.dialogs.help;

import alekseybykov.pets.mg.core.filereaders.direct.DirectStringsReader;
import alekseybykov.pets.mg.gui.components.helpers.GUIHelper;
import alekseybykov.pets.mg.gui.uiconsts.dialogs.help.HelpDialogNames;
import alekseybykov.pets.mg.gui.uiconsts.localresources.HtmlPaths;
import alekseybykov.pets.mg.gui.uiconsts.localresources.IconPaths;
import org.apache.commons.lang3.math.NumberUtils;

import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.nio.charset.StandardCharsets;

/**
 * @author bykov.alexey
 * @since 30.04.2022
 */
public class InformationDialog extends JDialog {

	private final DirectStringsReader directStringsReader = new DirectStringsReader();

	public InformationDialog() {
		setTitle(HelpDialogNames.HELP_TITLE.getName());
		setModal(true);

		JPanel containerPanel = new JPanel(new BorderLayout());
		containerPanel.add(makeImagePanel(), BorderLayout.NORTH);
		containerPanel.add(makeDescriptionPanel(), BorderLayout.CENTER);

		getContentPane().add(containerPanel);
		setResizable(false);
	}

	private JPanel makeImagePanel() {
		JPanel imagePanel = new JPanel(new BorderLayout());
		//JLabel imageLabel = new JLabel(GUIHelper.getImageIconByPath(IconPaths.MSG_GEN_IMAGE.getPath()));
		//imagePanel.add(imageLabel);
		return imagePanel;
	}

	private JPanel makeDescriptionPanel() {
		JEditorPane editorPane = new JEditorPane();
		HTMLEditorKit htmlEditorKit = new HTMLEditorKit();
		editorPane.setEditorKit(htmlEditorKit);
		editorPane.setContentType("text/html");
		editorPane.setEditable(false);

		GUIHelper.stylizeHtmlEditorKit(htmlEditorKit);
		Document document = htmlEditorKit.createDefaultDocument();
		editorPane.setDocument(document);
		editorPane.setText(directStringsReader.readFile(GUIHelper.loadDialogText(HtmlPaths.INSTRUCTION_TEXT.getPath()), StandardCharsets.UTF_8));
		editorPane.setCaretPosition(NumberUtils.INTEGER_ZERO);
		setTitle(HelpDialogNames.HELP_TITLE.getName());

		JScrollPane scrollPane = new JScrollPane(editorPane);
		setSize(new Dimension(600, 550));

		JPanel descriptionPanel = new JPanel(new BorderLayout());
		descriptionPanel.add(scrollPane);
		return descriptionPanel;
	}
}
