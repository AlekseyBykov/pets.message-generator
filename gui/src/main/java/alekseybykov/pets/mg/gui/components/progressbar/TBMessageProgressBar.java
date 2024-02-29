package alekseybykov.pets.mg.gui.components.progressbar;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;

/**
 * Прогресс-бар для таба "Обмен с OeBS - tb_message".
 *
 * Чтобы не таскать компонент через панели, таблицы и пр. до слушателей событий,
 * где он должен быть отображен/скрыт в процессе длительных операций,
 * создаем бин-синглтон. Можно инжектить его в разные вью, при этом добавить на лэйаут
 * возможно только один раз (т.к. это синглтон). При этом вызывать setVisible(true/false)
 * можно из каждого компонента, где он был инжектирован - это отразится только там, где он
 * был добавлен в лэйаут.
 *
 * (Возможно, правильнее сделать это через разделение прогресс-бара на модель и вью,
 * при этом модель инжектируется куда угодно, а вью - одно, обновляется из модели).
 *
 * @author bykov.alexey
 * @since 26.02.2024
 */
@Component
public class TBMessageProgressBar extends JProgressBar {

	@PostConstruct
	private void postConstruct() {
		setIndeterminate(true);
		setVisible(false);
	}
}
