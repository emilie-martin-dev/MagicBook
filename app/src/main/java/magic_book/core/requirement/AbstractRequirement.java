package magic_book.core.requirement;

import magic_book.core.file.JsonExportable;
import magic_book.core.file.json.RequirementJson;
import magic_book.core.game.BookState;
import magic_book.core.parser.Descriptible;

/**
 * Représente un prérequis
 */
public abstract class AbstractRequirement implements Descriptible, JsonExportable<RequirementJson>{

	/**
	 * Permet de savoir si un prérequis est satisfait
	 * @param state État de la partie
	 * @return true si la condition est satisfaite, false sinon
	 */
	public abstract boolean isSatisfied(BookState state);
	
}
