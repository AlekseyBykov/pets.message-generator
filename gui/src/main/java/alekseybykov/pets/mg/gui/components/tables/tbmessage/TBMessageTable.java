package alekseybykov.pets.mg.gui.components.tables.tbmessage;

import alekseybykov.pets.mg.core.models.table.TBMessageTableModel;
import alekseybykov.pets.mg.gui.components.listeners.menu.SendConfirmBodActionListener;
import alekseybykov.pets.mg.gui.components.listeners.tbmessage.TBMessageRowMouseClickListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * @author bykov.alexey
 * @since 19.04.2021
 */
@Component
public class TBMessageTable extends JTable {

	@Autowired
	private TBMessageTableModel tableModel;

	@Autowired
	private TBMessageRowMouseClickListener mouseClickListener;
	@Autowired
	private SendConfirmBodActionListener sendConfirmBodActionListener;

	@PostConstruct
	private void postConstruct() {
		initFields();
	}

	private void initFields() {
		setModel(tableModel);
		addMouseListener(mouseClickListener);
		getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		mouseClickListener.setPopupMenu(buildTablePopupMenu());
	}

	private JPopupMenu buildTablePopupMenu() {
		JPopupMenu popup = new JPopupMenu();
		JMenuItem item = new JMenuItem("Отправить ConfirmBod");
		item.addActionListener(sendConfirmBodActionListener);
		popup.add(item);
		return popup;
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
