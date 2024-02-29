package alekseybykov.pets.mg.gui.components.panels.admin.localpaths;

import alekseybykov.pets.mg.gui.components.tables.localpaths.LocalPathsTable;
import lombok.Getter;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * @author bykov.alexey
 * @since 26.12.2022
 */
@Component
public class LocalPathsTablePanel extends JPanel {

	@Getter
	@Autowired
	private LocalPathsTable localPathsTable;

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
		val panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(new JScrollPane(localPathsTable), BorderLayout.CENTER);

		return panel;
	}
}
