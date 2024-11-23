package alekseybykov.pets.mg.gui.components.panels.combobox;

import alekseybykov.pets.mg.core.businessobjects.TransportFolder;
import alekseybykov.pets.mg.core.models.combobox.TransportFolderComboBoxModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

@Component
public class TransportFoldersComboBoxPanel extends JPanel {

	@Autowired
	private TransportFolderComboBoxModel transportFolderComboBoxModel;

	private JComboBox<TransportFolder> comboBox;

	@PostConstruct
	private void postConstruct() {
		initFields();
		buildLayout();
	}

	private void initFields() {
		comboBox = new JComboBox<>(transportFolderComboBoxModel);
		comboBox.setPreferredSize(new Dimension(180, 30));
	}

	private void buildLayout() {
		setLayout(new BorderLayout());
		add(comboBox, BorderLayout.CENTER);
	}
}
