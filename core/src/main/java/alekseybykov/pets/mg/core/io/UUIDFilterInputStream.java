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

public class UUIDFilterInputStream extends FilterInputStream {

	private static final UILogger uiLogger = UILogger.getInstance();

	private final Deque<Integer> inputQueue = new LinkedList<>();
	private final Deque<Integer> outputQueue = new LinkedList<>();

	private final String placeholder;
	private final String replacement;

	public UUIDFilterInputStream(
			InputStream inputStream,
			String placeholder,
			String replacement
	) {
		super(inputStream);

		this.placeholder = placeholder;
		this.replacement = replacement;
	}

	@Override
	public int read() throws IOException {
		if (outputQueue.isEmpty()) {
			readBytesToInputQueue();

			if (isPlaceholdersFound()) {
				inputQueue.clear();
				populateOutputQueue();
			} else {
				outputQueue.add(inputQueue.remove());
			}
		}
		return outputQueue.remove();
	}

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

	private void readBytesToInputQueue() throws IOException {
		byte[] placeholderBytes = placeholder.getBytes(StandardCharsets.UTF_8);

		while (inputQueue.size() < placeholderBytes.length) {
			int next = super.read();
			inputQueue.offer(next);

			if (next == NumberUtils.INTEGER_MINUS_ONE) {
				break;
			}
		}
	}

	private void populateOutputQueue() {
		byte[] replacementBytes = new byte[0];

		if (
				StringUtils.equals(
						TemplatePlaceholders.UUID_PLACEHOLDER.getValue(), placeholder
				)
		) {
			if (replacement != null) {
				uiLogger.log("Установлен UUID = [<b>" + replacement + "</b>]<br/>");

				replacementBytes = replacement.getBytes(StandardCharsets.UTF_8);
			} else {
				String uuidString = UUID.randomUUID().toString().toUpperCase();
				uiLogger.log("Установлен UUID = [<b>" + uuidString + "</b>]<br/>");

				replacementBytes = uuidString.getBytes(StandardCharsets.UTF_8);
			}

		} else if (
				StringUtils.equals(
						TemplatePlaceholders.DOC_UUID_PLACEHOLDER.getValue(), placeholder
				)
		) {
			uiLogger.log("Установлен docUUID = [<b>" + replacement + "</b>]<br/>");
			replacementBytes = replacement.getBytes(StandardCharsets.UTF_8);
		} else if (
				StringUtils.equals(
						TemplatePlaceholders.RESPONSE_SENDER_PLACEHOLDER.getValue(), placeholder
				)
		) {
			uiLogger.log("Установлен Sender LogicalID = [<b>" + replacement + "</b>]<br/>");
			replacementBytes = replacement.getBytes(StandardCharsets.UTF_8);
		} else if (
				StringUtils.equals(
						TemplatePlaceholders.RESPONSE_RECIPIENT_PLACEHOLDER.getValue(),
						placeholder
				)
		) {
			uiLogger.log("Установлен Sender RecipientLogicalID = [<b>" + replacement + "</b>]<br/>");
			replacementBytes = replacement.getBytes(StandardCharsets.UTF_8);
		} else if (
				StringUtils.equals(
						TemplatePlaceholders.REQUEST_RECIPIENT_PLACEHOLDER.getValue(), placeholder
				)
		) {
			uiLogger.log("Установлен Original LogicalID = [<b>" + replacement + "</b>]<br/>");
			replacementBytes = replacement.getBytes(StandardCharsets.UTF_8);
		} else if (
				StringUtils.equals(
						TemplatePlaceholders.REQUEST_SENDER_PLACEHOLDER.getValue(), placeholder
				)
		) {
			uiLogger.log("Установлен Original RecipientLogicalID = [<b>" + replacement + "</b>]<br/>");
			replacementBytes = replacement.getBytes(StandardCharsets.UTF_8);
		}

		for (byte b : replacementBytes) {
			outputQueue.offer((int) b);
		}
	}
}
