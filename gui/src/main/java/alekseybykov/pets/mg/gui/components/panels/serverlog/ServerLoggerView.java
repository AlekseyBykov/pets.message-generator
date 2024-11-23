package alekseybykov.pets.mg.gui.components.panels.serverlog;

import alekseybykov.pets.mg.core.businessobjects.serverlog.ServerLog;
import alekseybykov.pets.mg.core.businessobjects.serverlog.ServerLogObserver;
import alekseybykov.pets.mg.gui.uiconsts.borders.BorderTitles;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

@Component
public class ServerLoggerView extends JPanel implements ServerLogObserver {

	private JTextArea textArea;

	@Autowired
	private ServerLog serverLog;

	@Override
	public void update(String logText) {
		textArea.setText(StringUtils.EMPTY);
		textArea.setText(logText);
		textArea.setCaretPosition(
				textArea.getDocument().getLength());
	}

	@PostConstruct
	private void postConstruct() {
		initFields();
		buildLayout();
	}

	private void initFields() {
		serverLog.addObserver(this);
		buildTextArea();
	}

	private void buildTextArea() {
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(false);
		textArea.setFont(new Font("Courier New", Font.BOLD, 12));
	}

	private void buildLayout() {
		setLayout(new BorderLayout());

		JScrollPane scrollPane = new JScrollPane(
				textArea,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		scrollPane.setBorder(BorderFactory.createTitledBorder(BorderTitles.SERVER_LOG.getTitle()));

		add(scrollPane);
	}
}
