package alekseybykov.pets.mg.core.models.complexmodels;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * Данные тулбара "Серверный лог", комплексная модель.
 *
 * @author bykov.alexey
 * @since 24.10.2023
 */
@Component
public class ServerToolbarModel {

	@Setter @Getter
	private String logfileName;
	@Setter @Getter
	private String logfileAbsolutePath;
}
