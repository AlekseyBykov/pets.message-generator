package alekseybykov.pets.mg.core.models.encoding;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
public class SqlLogEncodingModel implements EncodingModel {

	@Getter
	@Setter
	private String selectedEncoding = "ISO-8859-5";
}
