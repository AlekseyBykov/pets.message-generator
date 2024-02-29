package alekseybykov.pets.mg.gui.components.tables.guithemes;

import alekseybykov.pets.mg.core.models.table.GuiThemeTableModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * @author bykov.alexey
 * @since 25.02.2024
 */
@Component
public class GuiThemesTable extends JTable {

	@Autowired
	private GuiThemeTableModel model;

	@PostConstruct
	private void postConstruct() {
		initFields();
	}

	private void initFields() {
		setModel(model);
		getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	@Override
	public java.awt.Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
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
