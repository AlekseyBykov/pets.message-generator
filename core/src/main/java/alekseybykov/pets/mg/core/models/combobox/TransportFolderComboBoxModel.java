package alekseybykov.pets.mg.core.models.combobox;

import alekseybykov.pets.mg.core.businessobjects.TransportFolder;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author bykov.alexey
 * @since 21.02.2024
 */
@Component
public class TransportFolderComboBoxModel extends DefaultComboBoxModel<TransportFolder> implements ComboBoxModel<TransportFolder> {

	@Getter
	private List<TransportFolder> data = new ArrayList<>();

	private TransportFolder selectedItem;

	public void setData(List<TransportFolder> data) {
		this.data = data;
	}

	@Override
	public void setSelectedItem(Object item) {
		selectedItem = (TransportFolder) item;
	}

	@Override
	public TransportFolder getSelectedItem() {
		return selectedItem;
	}

	@Override
	public int getSize() {
		return data.size();
	}

	@Override
	public TransportFolder getElementAt(int index) {
		return data.get(index);
	}
}
