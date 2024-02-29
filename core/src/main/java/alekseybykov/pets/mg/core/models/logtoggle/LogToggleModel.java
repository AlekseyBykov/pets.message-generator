package alekseybykov.pets.mg.core.models.logtoggle;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * @author bykov.alexey
 * @since 28.02.2024
 */
@Component
public class LogToggleModel {

	@Setter
	@Getter
	private LogToggleOptions selectedOption = LogToggleOptions.DETAILED;
}
