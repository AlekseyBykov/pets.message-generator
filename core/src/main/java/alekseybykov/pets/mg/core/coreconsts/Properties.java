package alekseybykov.pets.mg.core.coreconsts;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Properties {

	USER_HOME_PROPERTY("user.home");

	private final String name;
}
