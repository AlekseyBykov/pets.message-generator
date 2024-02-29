package alekseybykov.pets.mg.gui.components.tables.sqllog;

import alekseybykov.pets.mg.core.models.table.SqlLogTableModel;
import alekseybykov.pets.mg.gui.components.listeners.sql.SqlLogTableMouseClickListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * @author bykov.alexey
 * @since 26.06.2022
 */
@Component
public class SqlLogTable extends JTable {

	@Autowired
	private SqlLogTableModel tableModel;
	@Autowired
	private SqlLogTableMouseClickListener mouseClickListener;

	@PostConstruct
	private void postConstruct() {
		initFields();
	}

	private void initFields() {
		setModel(tableModel);
		addMouseListener(mouseClickListener);
		getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	@Override
	public java.awt.Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
		java.awt.Component component = super.prepareRenderer(renderer, row, column);

		component.setFont(new Font("Courier New", Font.BOLD, 12));
		((DefaultTableCellRenderer)component).setHorizontalAlignment(SwingConstants.CENTER);

		if (isRowSelected(row)) {
			component.setBackground(new Color(189, 207, 231));
		} else {
			if (row % 2 == 0) {
				component.setBackground(new Color(255, 255, 255));
			} else {
				component.setBackground(new Color(248, 248, 248));
			}
		}

		return component;
	}
}
