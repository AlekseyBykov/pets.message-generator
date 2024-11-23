package alekseybykov.pets.mg.services.filefolder;

import alekseybykov.pets.mg.core.businessobjects.TransportFolder;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface FileFolderService {

	@NotNull
	List<TransportFolder> findAllSubFolders(@NotNull String rootPath);

	void sendFilesToTransportFolder(Map<String, File> files, TransportFolder transportFolder);
}
