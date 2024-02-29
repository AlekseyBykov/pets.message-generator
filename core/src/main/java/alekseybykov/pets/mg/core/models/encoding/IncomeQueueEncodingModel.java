package alekseybykov.pets.mg.core.models.encoding;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * @author bykov.alexey
 * @since 01.11.2023
 */
@Component
public class IncomeQueueEncodingModel implements EncodingModel {

	@Setter
	@Getter
	private String selectedEncoding = "ISO-8859-5"; // начальное значение кодировки.
}
