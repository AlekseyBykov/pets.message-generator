package alekseybykov.pets.mg.core.models.logtoggle;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
public class LogToggleModel {

	@Setter
	@Getter
	private LogToggleOptions selectedOption = LogToggleOptions.DETAILED;
}
