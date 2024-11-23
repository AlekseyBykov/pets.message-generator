package alekseybykov.pets.mg.gui.components.panels.serverlog;

import alekseybykov.pets.mg.core.models.encoding.ServerLogEncodingModel;
import alekseybykov.pets.mg.gui.components.buttons.choose.ChooseLogFileButton;
import alekseybykov.pets.mg.gui.components.buttons.toggle.ServerLogToggleButton;
import alekseybykov.pets.mg.gui.components.panels.common.EncodingView;
import alekseybykov.pets.mg.gui.components.textfields.ServerLogFilePathTextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

@Component
public class ServerLoggerToolbar extends JPanel {

	@Autowired
	private ServerLogFilePathTextField serverLogFilePathTextField;
	@Autowired
	private ChooseLogFileButton chooseLogFileButton;
	@Autowired
	private EncodingView encodingView;
	@Autowired
	private ServerLogEncodingModel encodingModel;
	@Autowired
	private ServerLogToggleButton startStopLoggerButton;

	@PostConstruct
	private void postConstruct() {
		initFields();
		buildLayout();
	}

	private void initFields() {
		encodingView.setModel(encodingModel);
	}

	private void buildLayout() {
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(10, 10, 10, 10));

		JPanel toolbarContainer = new JPanel();
		toolbarContainer.setLayout(new BoxLayout(toolbarContainer, BoxLayout.Y_AXIS));

		JPanel toolbar = new JPanel();
		toolbar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolbar.setBorder(new EmptyBorder(10, 10, 10, 10));

		JPanel logChooserContainer = new JPanel();
		logChooserContainer.setLayout(new FlowLayout(FlowLayout.LEFT));
		logChooserContainer.setBorder(BorderFactory.createTitledBorder("Лог-файл: "));
		logChooserContainer.add(chooseLogFileButton);
		logChooserContainer.add(serverLogFilePathTextField);

		toolbar.add(logChooserContainer);
		toolbar.add(encodingView);
		toolbar.add(startStopLoggerButton);

		toolbarContainer.add(toolbar);
		add(toolbarContainer, BorderLayout.CENTER);
	}
}
