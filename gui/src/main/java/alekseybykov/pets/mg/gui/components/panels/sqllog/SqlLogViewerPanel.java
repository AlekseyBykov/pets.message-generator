package alekseybykov.pets.mg.gui.components.panels.sqllog;

import alekseybykov.pets.mg.gui.components.blobviewers.SQLViewer;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

@Component
public class SqlLogViewerPanel extends JPanel {

	@Autowired
	private SQLViewer viewer;

	@PostConstruct
	private void postConstruct() {
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(10, 10, 10, 7));

		val scrollPane = new JScrollPane(viewer);
		scrollPane.setBorder(BorderFactory.createTitledBorder("SQL: "));

		add(scrollPane);
	}
}
