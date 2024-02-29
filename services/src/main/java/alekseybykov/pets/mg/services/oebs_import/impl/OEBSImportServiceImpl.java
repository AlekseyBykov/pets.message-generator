package alekseybykov.pets.mg.services.oebs_import.impl;

import alekseybykov.pets.mg.core.businessobjects.file.AttachmentFile;
import alekseybykov.pets.mg.core.businessobjects.file.OAGISFile;
import alekseybykov.pets.mg.dao.oebs_import.OEBSImportDao;
import alekseybykov.pets.mg.services.oebs_import.OEBSImportService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author bykov.alexey
 * @since 08.03.2021
 */
@Service
public class OEBSImportServiceImpl implements OEBSImportService {

	@Autowired
	private OEBSImportDao dao;

	@Override
	public void importOagisFileWithAttachmentFiles(@NotNull OAGISFile oagisFile,
	                                               @NotNull List<AttachmentFile> attachmentFiles) {
		dao.importOagisFileWithAttachmentFiles(oagisFile, attachmentFiles);
	}
}
