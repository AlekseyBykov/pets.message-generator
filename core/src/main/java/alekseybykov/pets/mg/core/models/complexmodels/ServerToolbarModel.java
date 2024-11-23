package alekseybykov.pets.mg.core.models.complexmodels;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
public class ServerToolbarModel {

	@Setter @Getter
	private String logfileName;
	@Setter @Getter
	private String logfileAbsolutePath;
}
