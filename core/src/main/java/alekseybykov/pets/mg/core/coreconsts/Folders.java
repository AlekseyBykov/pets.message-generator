package alekseybykov.pets.mg.core.coreconsts;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Folders {

	DB_FOLDER("db"),
	LOGS_FOLDER("logs"),
	TEMP_FOLDER("temp");

	@Getter
	private final String name;
}
