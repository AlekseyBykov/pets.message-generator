package alekseybykov.pets.mg.core.coreconsts;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ContentTypes {

	BINARY_UNKNOWN("binary/unknown"),
	TEXT_HTML("text/html"),
	TEXT_XML("text/xml");

	@Getter
	private final String type;
}
