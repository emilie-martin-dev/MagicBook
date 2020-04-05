package magic_book.core.parser;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import magic_book.core.game.BookCharacter;
import magic_book.core.item.BookItem;

/**
 * Parser pour un texte. Permet de transformer des références à un l'id d'un item, personnage, etc en un texte à afficher
 */
public class TextParser {

	/**
	 * Permet de parser un texte
	 * @param text Le texte à parser
	 * @param listItems La liste des items à parser
	 * @param listCharacters La liste des personnages à parser
	 * @return Le texte parsé
	 */
	public static String parseText(String text, HashMap<String, BookItem> listItems, HashMap<String, BookCharacter> listCharacters) {
		text = parseTextItems(text, listItems);
		text = parseTextCharacters(text, listCharacters);

		return text;
	}

	/**
	 * Parse les personnages
	 * @param text Le texte à parser
	 * @param listCharacters La liste des personnages à parser
	 * @return Le texte parsé
	 */
	private static String parseTextCharacters(String text, HashMap<String, BookCharacter> listCharacters) {
		return parseText(text, listCharacters, "##", "##");
	}

	/**
	 * Parse les items
	 * @param text Le texte à parser
	 * @param listItems La liste des items à parser
	 * @return Le texte parsé
	 */
	private static String parseTextItems(String text, HashMap<String, BookItem> listItems) {
		return parseText(text, listItems, "\\{\\{", "\\}\\}");
	}

	/**
	 * Parse le texte
	 * @param text Le texte à parser
	 * @param parsableMap La liste des Parsable que l'on peut trouver
	 * @param beginTag Le début de la chaine à parser
	 * @param endTag La fin de la chaine à parser
	 * @return Le texte parsé
	 */
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
