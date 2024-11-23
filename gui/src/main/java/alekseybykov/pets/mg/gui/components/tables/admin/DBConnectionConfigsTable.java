package alekseybykov.pets.mg.gui.components.tables.admin;

import alekseybykov.pets.mg.core.models.table.DBConnectionConfigsTableModel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

@Component
public class DBConnectionConfigsTable extends JTable {

	@Getter
	@Autowired
	private DBConnectionConfigsTableModel tableModel;

	@PostConstruct
	private void postConstruct() {
		initFields();
	}

	private void initFields() {
		setModel(tableModel);
		getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	@Override
	public java.awt.Component prepareRenderer(
			TableCellRenderer renderer,
			int row,
			int column
	) {
		java.awt.Component component = super.prepareRenderer(renderer, row, column);

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
