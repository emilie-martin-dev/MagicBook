package magic_book.core.parser;

import magic_book.core.Book;

/**
 * Interface pour décrire sous forme de String un objet
 */
public interface Descriptible {
	
	/**
	 * Exporte sous forme de chaine de String l'objet
	 * @param book Le livre en cours d'édition
	 * @return La description de l'objet
	 */
	public String getDescription(Book book);
	
}
