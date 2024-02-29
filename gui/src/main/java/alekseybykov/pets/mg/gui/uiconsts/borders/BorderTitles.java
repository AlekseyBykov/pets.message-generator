package alekseybykov.pets.mg.gui.uiconsts.borders;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author bykov.alexey
 * @since 24.02.2024
 */
@RequiredArgsConstructor
public enum BorderTitles {

	SELECTED_OAGIS("OAGIS: "),
	ATTACHMENT_LIST_TABLE("Список вложений: "),
	TFF_LIST_TABLE("Список ТФФ: "),

	ENCODING_SELECTOR("Просмотреть в кодировке: "),

	TB_MESSAGE_BLOB_CONTENT("OAGIS: "),
	TB_MESSAGE_BIG_ATTRIBUTES_BLOB_CONTENT("Вложение: "),

	INCOME_QUEUE_FILE("Файл входящей очереди: "),
	OUTCOME_QUEUE_FILE("Файл исходящей очереди: "),

	SERVER_LOG("Лог (последние 6 КБ): "),

	TRANSPORT_FOLDERS_COMBO_BOX_PANEL("Отправить в каталог: ");

	@Getter
	private final String title;
}
