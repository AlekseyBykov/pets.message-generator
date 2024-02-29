package alekseybykov.pets.mg.services.templating.impl;

import alekseybykov.pets.mg.core.businessobjects.file.TFFFile;
import alekseybykov.pets.mg.core.templating.TemplateProcessor;
import alekseybykov.pets.mg.services.templating.TemplatingService;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author bykov.alexey
 * @since 21.02.2024
 */
@Component
public class TemplatingServiceImpl implements TemplatingService {

	@NotNull
	@Override
	public Map<String, File> process(@NotNull List<TFFFile> tffFiles) {
		Map<String, File> processedFiles = new HashMap<>();
		for (TFFFile tffFile : tffFiles) {
			val uuid = UUID.randomUUID().toString().toLowerCase();
			File processedFile = TemplateProcessor.process(tffFile, uuid);
			processedFiles.put(tffFile.getName(), processedFile);
		}
		return processedFiles;
	}
}
