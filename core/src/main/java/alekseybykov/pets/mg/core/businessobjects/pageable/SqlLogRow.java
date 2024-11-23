package alekseybykov.pets.mg.core.businessobjects.pageable;

import alekseybykov.pets.mg.core.businessobjects.PageableData;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SqlLogRow implements PageableData {

	private String sqlId;
	private String firstLoadTime;
	/*private int parsingUserId;*/
	private String userName;
	private String parsingSchemaName;
	private String service;
	private String module;
	private String sqlText;
	private int discReads;
	private int rowsProcessed;
	private int elapsedTime;
}
