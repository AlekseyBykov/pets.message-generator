package alekseybykov.pets.mg.core.coreconsts;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum EncodingScheme {

	BITMIME("bitmime"),

	Base85("base85"),
	BASE16("base16"),
	Base32("base32"),
	BASE36("base36"),
	BASE62("base62"),
	BASE64("base64");

	@Getter
	private String scheme;
}
