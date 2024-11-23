package alekseybykov.pets.mg.core.businessobjects.pageable;

import alekseybykov.pets.mg.core.businessobjects.PageableData;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class OutcomeQueueTable implements PageableData {

	private int id;
	private String url;
	private Date createDate;
	private int seqNum;
	private int sequenceSize;
	private String guid;
	private String seqGuid;
	private int fileSize;
	private String contentClassname;
	private String status;
	private String errorMessage;
	private String errorCode;
	private String toComplexId;
	private Date changeStatusDate;
	private int priority;
	private int ownerDocsCount;
	private int processCount;
	private String info;
}
