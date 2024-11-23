package alekseybykov.pets.mg.core.filereaders.reverse;

import lombok.SneakyThrows;
import lombok.val;
import lombok.var;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;

@Component
public class ReverseStringsReader implements ReverseReader {

	private static final int PAGE_SIZE = 50;

	@SneakyThrows
	@NotNull
	@Override
	public String read(
			@NotNull String filePath,
			@NotNull String encoding
	) {
		if (isFileExists(filePath)) {
			LinkedList<String> logFileContent = readFileLineByLine(filePath);
			return readLastPage(logFileContent);
		}

		return StringUtils.EMPTY;
	}

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

	@NotNull
	private String readLastPage(@NotNull LinkedList<String> logFileContent) {
		List<String> flippedPage = getLastFlippedPage(logFileContent);
		return reversePage(flippedPage);
	}

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

	@NotNull
	private String reversePage(@NotNull List<String> flippedPage) {
		val page = new StringBuilder();
		val flippedPageSize = flippedPage.size();

		ListIterator<String> listIterator = flippedPage.listIterator(flippedPageSize);
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
