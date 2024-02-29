package alekseybykov.pets.mg.services.oebs_import;

import alekseybykov.pets.mg.core.businessobjects.file.AttachmentFile;
import alekseybykov.pets.mg.core.businessobjects.file.OAGISFile;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author bykov.alexey
 * @since 08.03.2021
 */
public interface OEBSImportService {

	void importOagisFileWithAttachmentFiles(@NotNull OAGISFile oagisFile,
	                                        @NotNull List<AttachmentFile> attachmentFiles);
}
