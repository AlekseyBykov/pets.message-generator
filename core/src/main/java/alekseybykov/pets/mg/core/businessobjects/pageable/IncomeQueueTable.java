package alekseybykov.pets.mg.core.businessobjects.pageable;

import alekseybykov.pets.mg.core.businessobjects.PageableData;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Входящая пакетная очередь СУФД.
 *
 * @author bykov.alexey
 * @since 16.04.2021
 */
@Getter
@Setter
@Builder
public class IncomeQueueTable implements PageableData {

	private int id;
	private String fileName;
	private Date receiveDate;
	private String seqGuid;
	private int fileSize;
	private byte[] blobContent;
	private String errorMessage;
	private String status;
	private String complexType;
	private int exported;
	private String errorCode;
	private Date changeStatusDate;
	private int priority;
	private int processCount;
	private int ownerDocsCount;
	private String obtainedThrough;
	private String info;
	private String recipient;
	private String sender;
}
