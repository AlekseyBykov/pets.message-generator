package alekseybykov.pets.mg.core.businessobjects;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Vector;

/**
 * @author bykov.alexey
 * @since 13.05.2021
 */
@Getter
@Setter
@Builder
public class TableRow {

	private Vector<String> headers;
	private Vector<Vector<String>> content;
}
