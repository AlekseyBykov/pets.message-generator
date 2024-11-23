package alekseybykov.pets.mg.gui.components.panels.common;

import alekseybykov.pets.mg.gui.components.helpers.GUIHelper;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static java.awt.GridBagConstraints.LINE_END;
import static java.awt.GridBagConstraints.LINE_START;

@Component
@Scope(value = "prototype")
public class LocalPathDetailsPanel extends JPanel {

	@Getter
	private JTextField nameTextField;
	@Getter
	private JTextField pathTextField;

	@PostConstruct
	private void postConstruct() {
		initFields();
		buildLayout();
	}

	private void initFields() {
		nameTextField = new JTextField(30);
		nameTextField.setEditable(false);

		pathTextField = new JTextField(30);
	}

	private void buildLayout() {
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(10, 10, 10, 10));

		JPanel localPathDetailsPanel = new JPanel();
		localPathDetailsPanel.setLayout(new GridBagLayout());
		localPathDetailsPanel.setBorder(BorderFactory.createTitledBorder(StringUtils.EMPTY));

		Insets rightPadding = GUIHelper.buildRightPadding(10);
		Insets bottomPadding = GUIHelper.buildBottomPadding(7);

		GridBagConstraints gbc = GUIHelper.buildGridBagConstraints();

		GUIHelper.addComponentToPanel(localPathDetailsPanel, new JLabel("name"), gbc, rightPadding, 0, 0, LINE_END);
		GUIHelper.addComponentToPanel(localPathDetailsPanel, nameTextField, gbc, bottomPadding, 1, 0, LINE_START);

		GUIHelper.addComponentToPanel(localPathDetailsPanel, new JLabel("path"), gbc, rightPadding, 0, 1, LINE_END);
		GUIHelper.addComponentToPanel(localPathDetailsPanel, pathTextField, gbc, bottomPadding, 1, 1, LINE_START);

		add(localPathDetailsPanel, BorderLayout.NORTH);
	}
}
