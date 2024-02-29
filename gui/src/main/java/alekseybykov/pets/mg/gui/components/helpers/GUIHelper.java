package alekseybykov.pets.mg.gui.components.helpers;

import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.InputStream;

/**
 * @author bykov.alexey
 * @since 17.02.2021
 */
public class GUIHelper {

	public static void stylizeHtmlEditorKit(HTMLEditorKit htmlEditorKit) {
		StyleSheet styleSheet = htmlEditorKit.getStyleSheet();
		styleSheet.addRule("body {color:#000; font-family:Verdana, sans-serif; margin: 4px; }");
		styleSheet.addRule("h3 {color:rgb(10, 94, 144); text-align:center;");
		styleSheet.addRule("p {text-ident:20px; }");
	}

	public static JLabel buildLabelWithImage(String title, String pathToIcon, int width, int height) {
		JLabel label = new JLabel(title);
		label.setIcon(scaleImageIcon(pathToIcon, width, height));

		return label;
	}

	public static ImageIcon getImageIconByPath(String pathToIcon) {
		return new ImageIcon(GUIHelper.class.getResource(pathToIcon));
	}

	public static ImageIcon scaleImageIcon(String pathToIcon, int width, int height) {
		ImageIcon imageIcon = getImageIconByPath(pathToIcon);
		Image image = imageIcon.getImage();
		Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return new ImageIcon(scaledImage);
	}

	public static JButton buildButton(int iconWidth, int iconHeight, String captionText,
	                                  String tooltipText, String pathToIcon, ActionListener clickListener) {
		JButton button = new JButton(captionText);

		if (StringUtils.isNotEmpty(tooltipText)) {
			button.setToolTipText(tooltipText);
		}

		if (StringUtils.isNotEmpty(pathToIcon) && iconWidth != 0 && iconHeight != 0) {
			button.setIcon(scaleImageIcon(pathToIcon, iconWidth, iconHeight));
		}

		if (clickListener != null) {
			button.addActionListener(clickListener);
		}

		button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		return button;
	}

	public static GridBagConstraints buildGridBagConstraints() {
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weighty = 0.1;
		return gridBagConstraints;
	}

	public static void addComponentToPanel(JPanel panel, Component component, GridBagConstraints gc,
	                                       Insets padding, int gridx, int gridy, int alignment) {
		gc.gridx = gridx;
		gc.gridy = gridy;
		gc.anchor = alignment;
		gc.insets = padding;
		panel.add(component, gc);
	}

	public static Insets buildRightPadding(int rightPaddingSize) {
		return new Insets(0, 0, 0, rightPaddingSize);
	}

	public static Insets buildBottomPadding(int bottomPaddingSize) {
		return new Insets(0, 0, bottomPaddingSize, 0);
	}

	public static InputStream loadDialogText(String filePath) {
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
	}
}
