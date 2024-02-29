package alekseybykov.pets.mg.core.models.table;

import alekseybykov.pets.mg.core.businessobjects.file.AttachmentFile;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * @author bykov.alexey
 * @since 01.05.2016
 */
@Component
public class AttachmentFileListTableModel extends AbstractTableModel {

	private static final String[] COLUMN_NAMES = {
		"Имя файла вложения (редактируемое поле)", "Content type (редактируемое поле)", "Путь к файлу", "Размер файла (байт)"
	};

	@Getter
	private final List<AttachmentFile> attachmentFiles = new ArrayList<>();

	@Override
	public int getRowCount() {
		return attachmentFiles.size();
	}

	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return COLUMN_NAMES[columnIndex];
	}

	public void addAttachmentFile(AttachmentFile attachment) {
		attachmentFiles.add(attachment);
		fireTableDataChanged();
	}

	@Override
	public void setValueAt(Object value, int row, int column) {
		if (column == 0) {
			attachmentFiles.get(row).setName((String) value);
		} else if (column == 1) {
			attachmentFiles.get(row).setContentType((String) value);
		}
		fireTableDataChanged();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		AttachmentFile attachmentFile = attachmentFiles.get(rowIndex);
		switch (columnIndex) {
			case 0: return attachmentFile.getName();
			case 1: return attachmentFile.getContentType();
			case 2: return attachmentFile.getPath();
			case 3: return attachmentFile.getSize();

			default: return null;
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex == 0 || columnIndex == 1;
	}

	public void removeSelectedRow(int rowIndex) {
		attachmentFiles.remove(rowIndex);
		fireTableDataChanged();
	}

	public void clear() {
		attachmentFiles.clear();
		fireTableDataChanged();
	}
}
