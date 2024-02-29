package alekseybykov.pets.mg.gui.components.textfields;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;

/**
 * @author bykov.alexey
 * @since 28.02.2024
 */
@Component
public class ServerLogFilePathTextField extends JTextField {

	@PostConstruct
	private void postConstruct() {
		setColumns(40);
		setEditable(false);
	}
}
