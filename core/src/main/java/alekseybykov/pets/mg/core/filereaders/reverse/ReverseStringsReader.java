package alekseybykov.pets.mg.core.filereaders.reverse;

import lombok.SneakyThrows;
import lombok.val;
import lombok.var;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;

/**
 * Класс выполняет считывание лог-файла СУФД (предполагается, что это будет именно лог-файл СУФД, хотя
 * можно указать и любой другой файл, он также будет считан) в обратном направлении с использованием
 * коллекций Java Collection.
 *
 * Считывется {@link #PAGE_SIZE} строк с конца.
 *
 * @author bykov.alexey
 * @since 23.10.2023
 */
@Component
public class ReverseStringsReader implements ReverseReader {

	// Число строк, считываемых с конца файла.
	private static final int PAGE_SIZE = 50;

	@SneakyThrows
	@NotNull
	@Override
	public String read(@NotNull String filePath, @NotNull String encoding) {
		if (isFileExists(filePath)) {
			LinkedList<String> logFileContent = readFileLineByLine(filePath);
			return readLastPage(logFileContent);
		}

		return StringUtils.EMPTY;
	}

	/**
	 * Метод считывает лог-файл построчно и сохряняет в LinkedList.
	 *
	 * @param filePath - путь к лог-файлу.
	 * @return - строки лог файла.
	 */
	@SneakyThrows
	@NotNull
	private LinkedList<String> readFileLineByLine(@NotNull String filePath) {
		Scanner scanner = new Scanner(new File(filePath));
		LinkedList<String> logFileContent = new LinkedList<>();
		while (scanner.hasNextLine()) {
			logFileContent.add(scanner.nextLine());
		}
		scanner.close();

		return logFileContent;
	}

	/**
	 * Метод считывает и возвращает последние {@link this#PAGE_SIZE} строк лог файла.
	 *
	 * @param logFileContent - все строки лог файла.
	 * @return - {@link this#PAGE_SIZE} строк лог файла.
	 */
	@NotNull
	private String readLastPage(@NotNull LinkedList<String> logFileContent) {
		List<String> flippedPage = getLastFlippedPage(logFileContent);
		return reversePage(flippedPage);
	}

	/**
	 * Из считанного в {@link this#readFileLineByLine} контента лог файла
	 * извлекаются последние {@link this#PAGE_SIZE} строк с конца.
	 * Строки помещаются в список, который возвращается данным методом.
	 * Т.о. в списке будет перевернутая страница лога (последняя строка лог-файла сверху).
	 *
	 * @param logFileContent - контент лог-сфала.
	 * @return - перевернутая страница лога.
	 */
	@NotNull
	private List<String> getLastFlippedPage(@NotNull LinkedList<String> logFileContent) {
		Iterator<String> iterator = logFileContent.descendingIterator();
		List<String> flippedPage = new ArrayList<>();
		var linesCounter = PAGE_SIZE;
		while (iterator.hasNext() && linesCounter > 0) {
			flippedPage.add(iterator.next());
			linesCounter--;
		}
		return flippedPage;
	}

	/**
	 * Метод инвертирует контент, полученный в методе {@link this#getLastFlippedPage}.
	 * Последняя страница лога переворачивается для вывода в UI.
	 *
	 * @param flippedPage - перевернутая страница лог-файла.
	 * @return - страница лог-файла в читабельном виде.
	 */
	@NotNull
	private String reversePage(@NotNull List<String> flippedPage) {
		val page = new StringBuilder();
		val flippedPageSize = flippedPage.size();

		// Positioning after the last element.
		ListIterator<String> listIterator = flippedPage.listIterator(flippedPageSize);
		// Iterate in reverse.
		while(listIterator.hasPrevious()) {
			page.append(listIterator.previous())
					.append("\n");
		}
		return page.toString();
	}

	private boolean isFileExists(@NotNull String filePath) {
		return new File(filePath).exists();
	}
}
