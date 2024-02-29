package alekseybykov.pets.mg.services.templating;

import alekseybykov.pets.mg.core.businessobjects.file.TFFFile;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author bykov.alexey
 * @since 21.02.2024
 */
public interface TemplatingService {

	//todo нужна отдельная сущность вместо Map.
	@NotNull
	Map<String, File> process(@NotNull List<TFFFile> tffFiles);
}
