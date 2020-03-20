package magic_book.core.parser;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import magic_book.core.game.BookCharacter;
import magic_book.core.item.BookItem;

public class TextParser {

	public static String parseText(String text, HashMap<String, BookItem> listItems, HashMap<String, BookCharacter> listCharacters) {
		text = parseTextItems(text, listItems);
		text = parseTextCharacters(text, listCharacters);

		return text;
	}

	private static String parseTextCharacters(String text, HashMap<String, BookCharacter> listCharacters) {
		return parseText(text, listCharacters, "##", "##");
	}

	private static String parseTextItems(String text, HashMap<String, BookItem> listItems) {
		return parseText(text, listItems, "\\{\\{", "\\}\\}");
	}

	private static String parseText(String text, HashMap<String, ? extends Parsable> parsableMap, String beginTag, String endTag) {
		Pattern pattern = Pattern.compile(beginTag + " *([a-zA-Z0-9]*) *" + endTag);
		Matcher matcher = pattern.matcher(text);

		while (matcher.find()) {
			String id = matcher.group(1);

			if(parsableMap.get(id) != null) {
				text = text.replaceAll(beginTag + " *" + id + " *" + endTag, parsableMap.get(id).getParsableText());
			}

			matcher = pattern.matcher(text);
		}

		return text;
	}
}
