package alekseybykov.pets.mg.gui.components.panels.admin.db;

import alekseybykov.pets.mg.gui.components.tables.admin.DBConnectionConfigsTable;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

@Component
public class DBConnectionConfigsTablePanel extends JPanel {

	@Getter
	@Autowired
	private DBConnectionConfigsTable table;

	@PostConstruct
	private void postConstruct() {
		buildLayout();
	}

	private void buildLayout() {
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(10, 10, 10, 10));
		add(buildScrolledTablePanel(), BorderLayout.CENTER);
	}

	private JPanel buildScrolledTablePanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(new JScrollPane(table), BorderLayout.CENTER);
		return panel;
	}
}
