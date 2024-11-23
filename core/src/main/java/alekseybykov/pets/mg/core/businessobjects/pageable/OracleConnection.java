package alekseybykov.pets.mg.core.businessobjects.pageable;

import alekseybykov.pets.mg.core.businessobjects.PageableData;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OracleConnection implements PageableData {

	private boolean active;
	private String name;
	private String url;
	private String driverClassName;
	private String user;
	private String password;
}
