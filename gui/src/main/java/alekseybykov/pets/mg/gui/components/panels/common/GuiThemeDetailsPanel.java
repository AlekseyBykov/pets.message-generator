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

/**
 * @author bykov.alexey
 * @since 25.02.2024
 */
@Component
@Scope(value = "prototype")
public class GuiThemeDetailsPanel extends JPanel {

	@Getter
	private JTextField nameTextField;
	@Getter
	private JCheckBox activeSign;

	@PostConstruct
	private void postConstruct() {
		initFields();
		buildLayout();
	}

	private void initFields() {
		nameTextField = new JTextField(30);
		nameTextField.setEditable(false);

		activeSign = new JCheckBox("Активная тема");
	}

	private void buildLayout() {
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(10, 10, 10, 10));

		JPanel configDetailsPanel = new JPanel();
		configDetailsPanel.setLayout(new GridBagLayout());
		configDetailsPanel.setBorder(BorderFactory.createTitledBorder(StringUtils.EMPTY));

		Insets rightPadding = GUIHelper.buildRightPadding(10);
		Insets bottomPadding = GUIHelper.buildBottomPadding(7);

		GridBagConstraints gbc = GUIHelper.buildGridBagConstraints();

		GUIHelper.addComponentToPanel(configDetailsPanel, new JLabel("Тема"), gbc, rightPadding, 0, 0, LINE_END);
		GUIHelper.addComponentToPanel(configDetailsPanel, nameTextField, gbc, bottomPadding, 1, 0, LINE_START);

		GUIHelper.addComponentToPanel(configDetailsPanel, activeSign, gbc, bottomPadding, 1, 1, LINE_START);

		add(configDetailsPanel, BorderLayout.NORTH);
	}
}
