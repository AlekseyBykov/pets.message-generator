package alekseybykov.pets.mg.gui.uiconsts;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author bykov.alexey
 * @since 28.12.2022
 */
@RequiredArgsConstructor
public enum Paginators {

	START_PAGE_NUMBER(1),
	END_PAGE_NUMBER(30),

	PAGE_SIZE(30),

	DISPLAYED_BUTTONS_NUMBER(10);

	@Getter
	private final int value;
}
