package alekseybykov.pets.mg.core.templating;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author bykov.alexey
 * @since 09.06.2022
 */
@AllArgsConstructor
public enum TemplatePlaceholders {

	UUID_PLACEHOLDER("$uuid"),
	DOC_UUID_PLACEHOLDER("$doc_uuid"),

	RESPONSE_SENDER_PLACEHOLDER("$response_sender"),
	RESPONSE_RECIPIENT_PLACEHOLDER("$response_recipient"),
	REQUEST_SENDER_PLACEHOLDER("$request_sender"),
	REQUEST_RECIPIENT_PLACEHOLDER("$request_recipient");

	@Getter
	private String value;
}
