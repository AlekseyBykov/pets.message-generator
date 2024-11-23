package alekseybykov.pets.mg.gui.components.editors;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ConnectionConfigColumnEditor extends DefaultCellEditor implements ItemListener {

	@Getter
	private final JRadioButton currentConnectionConfigSelector = new JRadioButton();

	public ConnectionConfigColumnEditor(JCheckBox checkBox) {
		super(checkBox);
	}

	public Component getTableCellEditorComponent(JTable table, Object value,
	                                             boolean isSelected, int row, int column) {
		if (value == null) {
			return null;
		}

		currentConnectionConfigSelector.addItemListener(this);

		if ((Boolean) value) {
			currentConnectionConfigSelector.setSelected(true);
		} else {
			currentConnectionConfigSelector.setSelected(false);
		}

		return currentConnectionConfigSelector;
	}

	public Object getCellEditorValue() {
		if (currentConnectionConfigSelector.isSelected()) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	public void itemStateChanged(ItemEvent itemEvent) {
		super.fireEditingStopped();
	}
}
