package alekseybykov.pets.mg.services.templating;

import alekseybykov.pets.mg.core.businessobjects.file.TFFFile;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface TemplatingService {

	@NotNull
	Map<String, File> process(@NotNull List<TFFFile> tffFiles);
}
