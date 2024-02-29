package alekseybykov.pets.mg.core.coreconsts;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author bykov.alexey
 * @since 28.12.2022
 */
@RequiredArgsConstructor
public enum ArchiveSignatures {

	ZIP_ARCHIVE_HEADER_BYTES(0x504b0304);

	@Getter
	private final int value;
}
