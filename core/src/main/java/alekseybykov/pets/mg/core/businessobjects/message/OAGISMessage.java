package alekseybykov.pets.mg.core.businessobjects.message;

import alekseybykov.pets.mg.core.businessobjects.file.OAGISFile;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Сообщение OAGIS. В отличие от {@link OAGISFile}, представляет
 * контент файла, разобранный парсером на составные блоки.
 *
 * @author bykov.alexey
 * @since 26.02.2024
 */
@Data
public class OAGISMessage {

	private ApplicationArea applicationArea;

	public void buildApplicationArea() {
		this.applicationArea = new ApplicationArea();
	}

	@Getter @Setter
	public static class ApplicationArea {
		private String senderLogicalID;
		private String recipientLogicalID;
	}
}
