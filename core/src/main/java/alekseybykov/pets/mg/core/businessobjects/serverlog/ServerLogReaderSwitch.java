package alekseybykov.pets.mg.core.businessobjects.serverlog;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
public class ServerLogReaderSwitch {

	@Getter @Setter
	private boolean enabled;
}
