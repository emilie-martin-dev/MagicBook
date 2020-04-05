package magic_book.core.parser;

/**
 * Interface permettant à un texte d'être parsé
 */
public interface Parsable {
	
	/**
	 * Donne l'id à parser
	 * @return L'id à parser
	 */
	public String getParsableId();

	/**
	 * Donne le texte à afficher au parser
	 * @return Le texte à afficher au parser
	 */
	public String getParsableText();
	
}
