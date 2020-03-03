package magic_book.core.utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import magic_book.core.BookCharacter;
import magic_book.core.item.BookItem;

public class TextParser {

	public static String parseText(String text, List<BookItem> listItems, List<BookCharacter> listCharacters) {
		text = parseTextItems(text, listItems);
		text = parseTextCharacters(text, listCharacters);

		return text;
	}

	private static String parseTextCharacters(String text, List<BookCharacter> listCharacters) {
		return parseText(text, listCharacters, "##", "##");
	}

	private static String parseTextItems(String text, List<BookItem> listItems) {
		return parseText(text, listItems, "\\{\\{", "\\}\\}");
	}

	private static String parseText(String text, List<? extends Parsable> parsableList, String beginTag, String endTag) {
		Pattern pattern = Pattern.compile(beginTag + " *([a-zA-Z0-9]*) *" + endTag);
		Matcher matcher = pattern.matcher(text);

		while (matcher.find()) {
			String id = matcher.group(1);

			for (Parsable p : parsableList) {
				if (id.equalsIgnoreCase(p.getParsableId())) {
					text = text.replaceAll(beginTag + " *" + id + " *" + endTag, p.getParsableText());
					break;
				}
			}

			matcher = pattern.matcher(text);
		}

		return text;
	}
}
