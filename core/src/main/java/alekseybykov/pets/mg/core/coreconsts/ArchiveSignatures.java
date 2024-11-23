package alekseybykov.pets.mg.core.coreconsts;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ArchiveSignatures {

	ZIP_ARCHIVE_HEADER_BYTES(0x504b0304);

	@Getter
	private final int value;
}
