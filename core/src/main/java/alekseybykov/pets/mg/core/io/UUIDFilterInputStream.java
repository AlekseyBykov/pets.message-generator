package alekseybykov.pets.mg.core.io;

import alekseybykov.pets.mg.core.templating.TemplatePlaceholders;
import alekseybykov.pets.mg.core.logging.UILogger;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.UUID;

/**
 * Поток, реализующий поиск и замену шаблонов $doc_uuid и $uuid в тексте на уровне байтов.
 * Данное решение позволяет абстрагироваться от информации о кодировке и обрабатывать raw bytes
 * как они есть.
 *
 * Например, загружаемый файл XML документа и/или вложения могут быть изначально
 * сформированы(записаны) в любой кодировке, и если искать шаблоны $doc_uuid и $uuid
 * используя SAX, DOM и пр. парсеры, либо методы String (Scanner построчно), то получаем:
 *
 *  bytes1[] -> chars[] -> bytes2[]
 *
 * При этом bytes2[] будет (возможно и чаще всего) отличен от bytes1[], т.к. если оперировать
 * не на уровне байтов, то при чтении и записи используется какая-либо кодировка (в Java это UTF-8).
 *
 * @author bykov.alexey
 * @since 07.03.2021
 */
public class UUIDFilterInputStream extends FilterInputStream {

	private static final UILogger uiLogger = UILogger.getInstance();

	private final Deque<Integer> inputQueue = new LinkedList<>();
	private final Deque<Integer> outputQueue = new LinkedList<>();

	private final String placeholder;
	private final String replacement;

	public UUIDFilterInputStream(InputStream inputStream, String placeholder, String replacement) {
		super(inputStream);

		this.placeholder = placeholder;
		this.replacement = replacement;
	}

	/**
	 * Метод выполняет чтение из инстанса данного потока.
	 */
	@Override
	public int read() throws IOException {
		// Изначально очередь outputQueue пуста.
		if (outputQueue.isEmpty()) {
			// Заполнить очередь inputQueue последовательно байтами файла до размеров искомого шаблона.
			readBytesToInputQueue();

			// Очередь inputQueue заполнена до размеров искомого шаблона в байтах.
			// Необходимо проверить, совпадают-ли байты (фрагмент файла), накопленные
			// в очереди inputQueue с байтами искомого шаблона ($doc_uuid или $uuid).
			if (isPlaceholdersFound()) {
				inputQueue.clear();
				populateOutputQueue();
			} else {
				// Если шаблон не найден - удаляем один байт из очереди inputQueue
				// и помещаем его в очередь outputQueue, чтобы в дальнейшем вернуть данный байт из нее.
				outputQueue.add(inputQueue.remove());
			}
		}

		// В вызывающий код, который выполняет чтение из данного потока, возвращается один прочитанный байт.
		return outputQueue.remove();
	}

	/**
	 *  Метод проверяет, совпадают-ли байты (фрагмент файла), накопленные
	 *  в очереди inputQueue с байтами искомого шаблона ($doc_uuid или $uuid).
	 */
	private boolean isPlaceholdersFound() {
		byte[] placeholderBytes = placeholder.getBytes(StandardCharsets.UTF_8);
		Iterator<Integer> iterator = inputQueue.iterator();
		for (byte b : placeholderBytes) {
			if (!iterator.hasNext() || b != iterator.next()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Метод побайтно заполняет очередь inputQueue. Очередь заполняется до размеров искомого шаблона ($doc_uuid или $uuid) в байтах.
	 * Например, если мы ищем шаблон $uuid, то его размер - 5 байтов. Очередь inputQueue будет всегда заполнена пятью байтами.
	 * При этом, в процессе чтения файла, 5-ти байтовый фрейм inputQueue будет двигаться по байтам файла.
	 * Очередь inputQueue всегда заполняется до размеров искомого шаблона, т.к. далее прочитанные в нее байты сравниваются с байтами шаблона
	 * (или можно представить, как считанная подстрока файла сравнивается со строкой шаблона), и в зависимости от результатов сравнения
	 * выполняется тот или иной код.
	 */
	private void readBytesToInputQueue() throws IOException {
		// Получить размер искомого шаблона а байтах.
		byte[] placeholderBytes = placeholder.getBytes(StandardCharsets.UTF_8);

		// Заполнять очередь inputQueue до размеров искомого шаблона в байтах.
		while (inputQueue.size() < placeholderBytes.length) {
			// Прочитать байт из файла.
			int next = super.read();
			// Поместить прочитанный байт в очередь inputQueue.
			inputQueue.offer(next);

			// Достигнут конец файла.
			if (next == NumberUtils.INTEGER_MINUS_ONE) {
				break;
			}
		}
	}

	/**
	 * Метод заполняет очередь outputQueue. В данной очереди будут содержаться байты значения, которым
	 * заменяется искомый шаблон:
	 * 1) Если найденный шаблон - $uuid, то будет сгенерирован случайный UUID (если в replacement не передано значение для него)
	 * и байты этого случайного UUID будут помещены в outputQueue;
	 * 2) Если найденный шаблон - $doc_uuid, то в очередь outputQueue будут помещены байты того значения,
	 * которое было передано в конструктор PreprocessedInputStream (replacement).
	 */
	private void populateOutputQueue() {
		byte[] replacementBytes = new byte[0];

		if (StringUtils.equals(TemplatePlaceholders.UUID_PLACEHOLDER.getValue(), placeholder)) {
			if (replacement != null) {
				uiLogger.log("Установлен UUID = [<b>" + replacement + "</b>]<br/>");

				replacementBytes = replacement.getBytes(StandardCharsets.UTF_8);
			} else {
				String uuidString = UUID.randomUUID().toString().toUpperCase();
				uiLogger.log("Установлен UUID = [<b>" + uuidString + "</b>]<br/>");
				replacementBytes = uuidString.getBytes(StandardCharsets.UTF_8);
			}

		} else if (StringUtils.equals(TemplatePlaceholders.DOC_UUID_PLACEHOLDER.getValue(), placeholder)) {
			uiLogger.log("Установлен docUUID = [<b>" + replacement + "</b>]<br/>");
			replacementBytes = replacement.getBytes(StandardCharsets.UTF_8);
		} else if (StringUtils.equals(TemplatePlaceholders.RESPONSE_SENDER_PLACEHOLDER.getValue(), placeholder)) {
			uiLogger.log("Установлен Sender LogicalID = [<b>" + replacement + "</b>]<br/>");
			replacementBytes = replacement.getBytes(StandardCharsets.UTF_8);
		} else if (StringUtils.equals(TemplatePlaceholders.RESPONSE_RECIPIENT_PLACEHOLDER.getValue(), placeholder)) {
			uiLogger.log("Установлен Sender RecipientLogicalID = [<b>" + replacement + "</b>]<br/>");
			replacementBytes = replacement.getBytes(StandardCharsets.UTF_8);
		} else if (StringUtils.equals(TemplatePlaceholders.REQUEST_RECIPIENT_PLACEHOLDER.getValue(), placeholder)) {
			uiLogger.log("Установлен Original LogicalID = [<b>" + replacement + "</b>]<br/>");
			replacementBytes = replacement.getBytes(StandardCharsets.UTF_8);
		} else if (StringUtils.equals(TemplatePlaceholders.REQUEST_SENDER_PLACEHOLDER.getValue(), placeholder)) {
			uiLogger.log("Установлен Original RecipientLogicalID = [<b>" + replacement + "</b>]<br/>");
			replacementBytes = replacement.getBytes(StandardCharsets.UTF_8);
		}

		for (byte b : replacementBytes) {
			outputQueue.offer((int) b);
		}
	}
}
