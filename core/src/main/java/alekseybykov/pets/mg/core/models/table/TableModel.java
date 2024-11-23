package alekseybykov.pets.mg.core.models.table;

import alekseybykov.pets.mg.core.businessobjects.PageableData;

import java.util.List;

public interface TableModel {

	void setData(List<PageableData> data);

	void updateTable();
}
