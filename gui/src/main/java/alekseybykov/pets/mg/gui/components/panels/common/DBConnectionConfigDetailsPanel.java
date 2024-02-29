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
 * @since 13.05.2021
 */
@Component
@Scope(value = "prototype")
public class DBConnectionConfigDetailsPanel extends JPanel {

	@Getter
	private JTextField nameTextField;
	@Getter
	private JTextField urlTextField;
	@Getter
	private JTextField driverClassNameTextField;
	@Getter
	private JTextField userTextField;
	@Getter
	private JTextField passwordTextField;
	@Getter
	private JCheckBox activeSign;

	@PostConstruct
	private void postConstruct() {
		initFields();
		buildLayout();
	}

	private void initFields() {
		nameTextField = new JTextField(30);
		urlTextField = new JTextField(30);
		driverClassNameTextField = new JTextField(30);
		userTextField = new JTextField(30);
		passwordTextField = new JTextField(30);
		activeSign = new JCheckBox("Активная конфигурация");
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

		GUIHelper.addComponentToPanel(configDetailsPanel, new JLabel("name"), gbc, rightPadding, 0, 0, LINE_END);
		GUIHelper.addComponentToPanel(configDetailsPanel, nameTextField, gbc, bottomPadding, 1, 0, LINE_START);

		GUIHelper.addComponentToPanel(configDetailsPanel, new JLabel("url"), gbc, rightPadding, 0, 1, LINE_END);
		GUIHelper.addComponentToPanel(configDetailsPanel, urlTextField, gbc, bottomPadding, 1, 1, LINE_START);

		GUIHelper.addComponentToPanel(configDetailsPanel, new JLabel("driver"), gbc, rightPadding, 0, 2, LINE_END);
		GUIHelper.addComponentToPanel(configDetailsPanel, driverClassNameTextField, gbc, bottomPadding, 1, 2, LINE_START);

		GUIHelper.addComponentToPanel(configDetailsPanel, new JLabel("user"), gbc, rightPadding, 0, 3, LINE_END);
		GUIHelper.addComponentToPanel(configDetailsPanel, userTextField, gbc, bottomPadding, 1, 3, LINE_START);

		GUIHelper.addComponentToPanel(configDetailsPanel, new JLabel("password"), gbc, rightPadding, 0, 4, LINE_END);
		GUIHelper.addComponentToPanel(configDetailsPanel, passwordTextField, gbc, bottomPadding, 1, 4, LINE_START);

		GUIHelper.addComponentToPanel(configDetailsPanel, activeSign, gbc, bottomPadding, 1, 5, LINE_START);

		add(configDetailsPanel, BorderLayout.NORTH);
	}
}
