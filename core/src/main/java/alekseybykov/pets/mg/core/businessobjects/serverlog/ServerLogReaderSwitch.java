package alekseybykov.pets.mg.core.businessobjects.serverlog;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * @author bykov.alexey
 * @since 24.10.2024
 */
@Component
public class ServerLogReaderSwitch {

	@Getter @Setter
	private boolean enabled;
}
