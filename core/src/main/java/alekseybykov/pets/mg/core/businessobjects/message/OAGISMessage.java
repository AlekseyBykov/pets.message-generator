package alekseybykov.pets.mg.core.businessobjects.message;

import alekseybykov.pets.mg.core.businessobjects.file.OAGISFile;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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
