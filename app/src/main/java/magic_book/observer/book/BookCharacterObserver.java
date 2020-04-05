package magic_book.observer.book;

import magic_book.core.game.BookCharacter;

/**
 * Observer pour réagir aux changements sur les personnages
 */
public interface BookCharacterObserver {

	/**
	 * Un personnage a été ajouté
	 * @param character Le personnage ajouté
	 */
	public void characterAdded(BookCharacter character);

	/**
	 * Un personnage a été mis a jour
	 * @param oldCharacter L'ancien personnage
	 * @param newCharacter Le nouveau personnage
	 */
	public void characterEdited(BookCharacter oldCharacter, BookCharacter newCharacter);

	/**
	 * Un personnage a été supprimé
	 * @param character Le personnage supprimé
	 */
	public void characterDeleted(BookCharacter character);
	
}
