package alekseybykov.pets.mg.gui.components.panels.sqllog;

import alekseybykov.pets.mg.gui.components.helpers.GUIHelper;
import alekseybykov.pets.mg.gui.components.listeners.sql.SerachSqlLogTextClickListener;
import alekseybykov.pets.mg.gui.uiconsts.localresources.IconPaths;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

@Component
public class SqlLogSearchPanel extends JPanel {

	@Getter
	private JTextField sqlLexemTextField;
	@Autowired
	private SerachSqlLogTextClickListener serachSqlLogTextClickListener;

	@PostConstruct
	private void postConstruct() {
		setLayout(new FlowLayout(FlowLayout.LEFT));
		setBorder(BorderFactory.createTitledBorder("Текст запроса содержит: "));

		sqlLexemTextField = new JTextField(20);
		sqlLexemTextField.setEditable(true);

		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));

		panel.add(GUIHelper.buildButton(20, 20, "Найти", StringUtils.EMPTY,
				IconPaths.SEARCH_BTN_ICON.getPath(), serachSqlLogTextClickListener));

		panel.add(sqlLexemTextField);
		add(panel);
	}
}
