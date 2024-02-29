package alekseybykov.pets.mg.gui.uiconsts.tabs;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author bykov.alexey
 * @since 24.02.2024
 */
@RequiredArgsConstructor
public enum TabTitles {

	OEBS_EXCHANGE_TAB("Обмен с OeBS"),
	OEBS_EXPORT_TAB("Экспорт в OeBS"),
	OEBS_IMPORT_TAB("Импорт из OeBS"),
	TB_MESSAGE_TAB_PANEL("tb_message"),
	TB_MESSAGE_BIG_ATTRIBUTES_TAB_PANEL("tb_message_big_attributes"),

	PACKET_QUEUE_TAB("Пакетная очередь"),
	INCOME_QUEUE_TAB("Входящая очередь"),
	OUTCOME_QUEUE_TAB("Исходящая очередь"),

	io("");


	@Getter
	private final String title;

}
