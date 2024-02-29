package alekseybykov.pets.mg.core.businessobjects.pageable;

import alekseybykov.pets.mg.core.businessobjects.PageableData;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author bykov.alexey
 * @since 18.03.2021
 */
@Getter
@Setter
@Builder
public class TBMessageBigAttributesTable implements PageableData {

	private int tbMsgId;
	private String type;
	private String name;
	private String contentType;
	private String description;
	private byte[] attachmentBytes;
	private int ordinalNumber;
	private String docGuid;
	private int id;
	private String attGuid;
	private Date preparedDate;
	private byte[] digitalSignaturesBytes;
}
