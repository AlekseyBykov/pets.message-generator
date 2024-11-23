package alekseybykov.pets.mg.dao.oebs_import;

import alekseybykov.pets.mg.core.businessobjects.file.AttachmentFile;
import alekseybykov.pets.mg.core.businessobjects.file.OAGISFile;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface OEBSImportDao {

	void importOagisFileWithAttachmentFiles(@NotNull OAGISFile oagisFile,
	                                        @NotNull List<AttachmentFile> attachmentFiles);
}
