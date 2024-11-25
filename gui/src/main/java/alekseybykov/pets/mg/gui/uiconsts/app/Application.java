package alekseybykov.pets.mg.gui.uiconsts.app;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Application {

	VENDOR_NAME(""),

	APPLICATION_NAME("Message Generator"),
	APPLICATION_VERSION("v7.1"),

	PLATFORM_DETAILS("Java desktop application");

	@Getter
	private final String title;
}
