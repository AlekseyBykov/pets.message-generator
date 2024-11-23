package alekseybykov.pets.mg.gui.components.panels.common;

import alekseybykov.pets.mg.core.models.encoding.EncodingModel;
import alekseybykov.pets.mg.gui.uiconsts.borders.BorderTitles;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
@Scope(value = "prototype")
public class EncodingView extends JPanel {

	@Setter
	private EncodingModel model;

	private final EncodingListener encodingListener = new EncodingListener();

	@PostConstruct
	private void postConstruct() {
		setLayout(new FlowLayout(FlowLayout.LEFT));
		setBorder(BorderFactory.createTitledBorder(BorderTitles.ENCODING_SELECTOR.getTitle()));

		JRadioButton utf8 = new JRadioButton(EncodingLabels.UTF_8.getLabel());
		utf8.setActionCommand(EncodingLabels.UTF_8.getLabel());
		utf8.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		utf8.addActionListener(encodingListener);

		JRadioButton win1251 = new JRadioButton(EncodingLabels.WIN_1251.getLabel());
		win1251.setActionCommand(EncodingLabels.WIN_1251.getLabel());
		win1251.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		win1251.addActionListener(encodingListener);

		JRadioButton iso88595 = new JRadioButton(EncodingLabels.ISO_8859_5.getLabel());
		iso88595.setActionCommand(EncodingLabels.ISO_8859_5.getLabel());
		iso88595.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		iso88595.setSelected(true);
		iso88595.addActionListener(encodingListener);

		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(utf8);
		buttonGroup.add(win1251);
		buttonGroup.add(iso88595);

		add(utf8);
		add(win1251);
		add(iso88595);
	}

	private class EncodingListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			JRadioButton rb = (JRadioButton)event.getSource();
			model.setSelectedEncoding(rb.getActionCommand());
		}
	}

	@RequiredArgsConstructor
	private enum EncodingLabels {

		WIN_1251("WINDOWS-1251"),
		ISO_8859_5("ISO-8859-5"),
		UTF_8("UTF-8");

		@Getter
		private final String label;
	}
}
