package alekseybykov.pets.mg.core.models.table;

import alekseybykov.pets.mg.core.businessobjects.PageableData;
import alekseybykov.pets.mg.core.businessobjects.pageable.OracleConnection;
import alekseybykov.pets.mg.core.db.oracle.OracleConnectionSwitcher;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * @author bykov.alexey
 * @since 25.06.2021
 */
@Component
public class DBConnectionConfigsTableModel extends AbstractTableModel implements TableModel {

	private static final String[] COLUMN_NAMES = {
			"active", "name", "url", "driverClassName", "user", "password"
	};

	private List<PageableData> data = new ArrayList<>();

	@Override
	public int getRowCount() {
		if (data == null) {
			return NumberUtils.INTEGER_ZERO;
		}
		return data.size();
	}

	@Override
	public int getColumnCount() {
		return 6;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return (columnIndex == 0) ? Boolean.class : String.class;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (data == null) {
			return null;
		}

		OracleConnection oracleConnection = (OracleConnection) data.get(rowIndex);
		switch (columnIndex) {
			case 0: return oracleConnection.isActive();
			case 1: return oracleConnection.getName();
			case 2: return oracleConnection.getUrl();
			case 3: return oracleConnection.getDriverClassName();
			case 4: return oracleConnection.getUser();
			case 5: return oracleConnection.getPassword();

			default: return null;
		}
	}

	public void addNewConfig(OracleConnection newConfig) {
		if (newConfig.isActive()) {
			OracleConnectionSwitcher.getInstance().deactivateConfigs();
			OracleConnectionSwitcher.getInstance().setActiveConnection(newConfig);
		}

		data.add(newConfig);
		fireTableDataChanged();
	}

	public void updateConfig(OracleConnection udatedConfig, int rowIndex) {
		if (udatedConfig.isActive()) {
			OracleConnectionSwitcher.getInstance().deactivateConfigs();
			OracleConnectionSwitcher.getInstance().setActiveConnection(udatedConfig);
		}

		OracleConnection oracleConnection = ((OracleConnection) data.get(rowIndex));
		if (oracleConnection.isActive() && !udatedConfig.isActive()) {
			OracleConnectionSwitcher.getInstance().resetConnection();
		}

		oracleConnection.setActive(udatedConfig.isActive());
		oracleConnection.setName(udatedConfig.getName());
		oracleConnection.setUser(udatedConfig.getUser());
		oracleConnection.setPassword(udatedConfig.getPassword());
		oracleConnection.setDriverClassName(udatedConfig.getDriverClassName());
		oracleConnection.setUrl(udatedConfig.getUrl());

		fireTableDataChanged();
	}

	public void removeConfig(int rowIndex) {
		OracleConnection oracleConnection = ((OracleConnection) data.get(rowIndex));
		if (oracleConnection.isActive()) {
			OracleConnectionSwitcher.getInstance().resetConnection();
		}

		data.remove(rowIndex);
		fireTableDataChanged();
	}

	@Override
	public String getColumnName(int columnIdx) {
		return COLUMN_NAMES[columnIdx];
	}

	@Override
	public void setData(List<PageableData> data) {
		this.data = data;
	}

	@Override
	public void updateTable() {
		super.fireTableDataChanged();
	}
}
