package alekseybykov.pets.mg.core.logging;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

@Component
public class LogPanel extends JPanel {

	private final LogTextArea logTextArea = LogTextArea.getInstance();

	@PostConstruct
	private void postConstruct() {
		buildLayout();
	}

	private void buildLayout() {
		setLayout(new BorderLayout());

		JScrollPane scrollPane = new JScrollPane(logTextArea);
		scrollPane.setBorder(BorderFactory.createTitledBorder("Лог: "));

		add(scrollPane);
	}
}
