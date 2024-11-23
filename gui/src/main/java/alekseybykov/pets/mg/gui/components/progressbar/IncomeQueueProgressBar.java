package alekseybykov.pets.mg.gui.components.progressbar;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;

@Component
public class IncomeQueueProgressBar extends JProgressBar {

	@PostConstruct
	private void postConstruct() {
		setIndeterminate(true);
		setVisible(false);
	}
}
