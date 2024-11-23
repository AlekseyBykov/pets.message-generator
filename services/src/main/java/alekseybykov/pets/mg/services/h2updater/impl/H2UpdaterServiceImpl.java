package alekseybykov.pets.mg.services.h2updater.impl;

import alekseybykov.pets.mg.core.filereaders.direct.DirectStringsReader;
import alekseybykov.pets.mg.core.utils.paths.PathUtils;
import alekseybykov.pets.mg.dao.h2updater.H2UpdaterDao;
import alekseybykov.pets.mg.services.h2updater.H2UpdaterService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Service
public class H2UpdaterServiceImpl implements H2UpdaterService {

	@Autowired
	private DirectStringsReader directStringsReader;
	@Autowired
	private H2UpdaterDao h2UpdaterDAO;

	@Override
	public void applyUpdates() {
		File scriptsFolder = new File(PathUtils.getScriptsFolderPath());
		for (File scriptFile : Objects.requireNonNull(scriptsFolder.listFiles())) {
			val scriptText = directStringsReader.readFile(scriptFile, StandardCharsets.UTF_8);
			h2UpdaterDAO.applyUpdates(scriptText);
		}
	}
}
