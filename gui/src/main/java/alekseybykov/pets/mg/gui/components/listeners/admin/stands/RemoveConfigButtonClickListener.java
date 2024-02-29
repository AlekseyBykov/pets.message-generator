package alekseybykov.pets.mg.gui.components.listeners.admin.stands;

import alekseybykov.pets.mg.core.businessobjects.pageable.OracleConnection;
import alekseybykov.pets.mg.core.coreconsts.ErrorMessages;
import alekseybykov.pets.mg.core.exceptions.RowIndexOutOfBoundsException;
import alekseybykov.pets.mg.gui.components.tables.admin.DBConnectionConfigsTable;
import alekseybykov.pets.mg.services.dbconnection.DBConnectionService;
import lombok.SneakyThrows;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author bykov.alexey
 * @since 27.06.2021
 */
@Component
public class RemoveConfigButtonClickListener implements ActionListener {

	private final static Object[] MODAL_DIALOG_OPTIONS = { "Да", "Нет" };

	@Autowired
	private DBConnectionConfigsTable table;

	@Autowired
	private DBConnectionService dbConnectionService;

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		int selectedRow = table.getSelectedRow();
		if (selectedRow == NumberUtils.INTEGER_MINUS_ONE) {
			JOptionPane.showMessageDialog(new JFrame(), ErrorMessages.CONFIG_NOT_SELECTED_FOR_REMOVE_ERROR_TEXT.getName(),
					ErrorMessages.REMOVE_CONFIG_ERROR.getName(),
					JOptionPane.ERROR_MESSAGE);
			throw new RowIndexOutOfBoundsException(ErrorMessages.CONFIG_NOT_SELECTED_FOR_REMOVE_ERROR_TEXT.getName());
		}

		int opt = JOptionPane.showOptionDialog(new JFrame(), "Удалить выбранную конфигурацию?",
				"Удаление конфигурации", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, MODAL_DIALOG_OPTIONS, MODAL_DIALOG_OPTIONS[0]);
		if (opt == NumberUtils.INTEGER_ZERO) {
			final OracleConnection removedConfig = getConfigFromTable();

			SwingWorker<Boolean, Void> configSaver = new SwingWorker<Boolean, Void>() {
				@Override
				@SneakyThrows
				protected Boolean doInBackground() {
					dbConnectionService.removeConfig(removedConfig);
					return true;
				}

				@Override
				@SneakyThrows
				protected void done() {
					if (get()) {
						table.getTableModel().removeConfig(table.getSelectedRow());
					}
				}
			};
			configSaver.execute();
		}
	}

	private OracleConnection getConfigFromTable() {
		return OracleConnection.builder()
				.active((Boolean) table.getModel().getValueAt(table.getSelectedRow(), 0))
				.name((String) table.getModel().getValueAt(table.getSelectedRow(), 1))
				.url((String) table.getModel().getValueAt(table.getSelectedRow(), 2))
				.driverClassName((String) table.getModel().getValueAt(table.getSelectedRow(), 3))
				.user((String) table.getModel().getValueAt(table.getSelectedRow(), 4))
				.password((String) table.getModel().getValueAt(table.getSelectedRow(), 5))
				.build();
	}
}
