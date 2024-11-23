package alekseybykov.pets.mg.gui.components.listeners.menu;

import alekseybykov.pets.mg.core.logging.LogTextArea;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
public class ClearSenderLogItemClickListener implements ActionListener {

	private final static Object[] MODAL_DIALOG_OPTIONS = { "Да", "Нет" };

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		int opt = JOptionPane.showOptionDialog(
				null,
				"Вы действительно хотите очистить информацию о процессе генерации?",
				"Подтвердите выбор",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				MODAL_DIALOG_OPTIONS, MODAL_DIALOG_OPTIONS[0]
		);
		if (opt == NumberUtils.INTEGER_ZERO) {
			LogTextArea.getInstance().clear();
			LogTextArea.getInstance().clear();
		}
	}
}
