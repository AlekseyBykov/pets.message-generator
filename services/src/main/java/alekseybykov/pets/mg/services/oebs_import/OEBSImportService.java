package alekseybykov.pets.mg.services.oebs_import;

import alekseybykov.pets.mg.core.businessobjects.file.AttachmentFile;
import alekseybykov.pets.mg.core.businessobjects.file.OAGISFile;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface OEBSImportService {

	void importOagisFileWithAttachmentFiles(@NotNull OAGISFile oagisFile,
	                                        @NotNull List<AttachmentFile> attachmentFiles);
}
