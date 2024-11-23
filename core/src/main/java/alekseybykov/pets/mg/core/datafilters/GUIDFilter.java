package alekseybykov.pets.mg.core.datafilters;

import lombok.SneakyThrows;
import lombok.val;
import lombok.var;

import javax.swing.text.AttributeSet;
import javax.swing.text.DocumentFilter;

public class GUIDFilter extends DocumentFilter {

	@SneakyThrows
	@Override
	public void replace(
			FilterBypass fb,
			int i,
			int i1,
			String string,
			AttributeSet as
	) {
		for (var n = string.length(); n > 0; n--) {
			char c = string.charAt(n - 1);

			val numbers = "0123456789";
			if (Character.isAlphabetic(c) || c == '-' || numbers.indexOf(c) > -1) {
				super.replace(fb, i, i1, String.valueOf(c), as);
			}
		}
	}

	@SneakyThrows
	@Override
	public void remove(
			FilterBypass fb,
			int i,
			int i1
	) {
		super.remove(fb, i, i1);
	}

	@SneakyThrows
	@Override
	public void insertString(
			FilterBypass fb,
			int i,
			String string,
			AttributeSet as
	) {
		super.insertString(fb, i, string, as);
	}
}
