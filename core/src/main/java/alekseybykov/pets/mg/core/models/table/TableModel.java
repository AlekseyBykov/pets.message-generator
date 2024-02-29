package alekseybykov.pets.mg.core.models.table;

import alekseybykov.pets.mg.core.businessobjects.PageableData;

import java.util.List;

/**
 * @author bykov.alexey
 * @since 31.05.2021
 */
public interface TableModel {

	void setData(List<PageableData> data);

	void updateTable();
}
