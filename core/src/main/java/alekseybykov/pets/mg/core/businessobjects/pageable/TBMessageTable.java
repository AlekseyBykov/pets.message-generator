package alekseybykov.pets.mg.core.businessobjects.pageable;

import alekseybykov.pets.mg.core.businessobjects.PageableData;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author bykov.alexey
 * @since 14.03.2021
 */
@Getter
@Setter
@Builder
public class TBMessageTable implements PageableData {

	private String id;
	private String routeMarker;
	private int sysFromCode;
	private int sysToCode;
	private int priority;
	private String queueName;
	private String description;
	private Date timeCreate;
	private Date timeAccept;
	private Date timeFinish;
	private int status;
	private String errorCode;
	private String errorText;
	private Date timeLastProcessed;
	private int processedCount;
	private byte[] bytes;
}
