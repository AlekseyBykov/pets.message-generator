package alekseybykov.pets.mg.core.coreconsts;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author bykov.alexey
 * @since 28.12.2022
 */
@Getter
@RequiredArgsConstructor
public enum Properties {

	USER_HOME_PROPERTY("user.home");

	private final String name;
}
