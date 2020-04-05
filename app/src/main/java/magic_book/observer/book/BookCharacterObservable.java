package magic_book.observer.book;

import magic_book.core.game.BookCharacter;
import magic_book.observer.Observable;

/**
 * Permet de notifier d'un changement sur les personnages du livre
 */
public class BookCharacterObservable extends Observable<BookCharacterObserver> {

	/**
	 * Notifie qu'un personnage a été ajouté
	 * @param bookCharacter Le personnage ajouté
	 */
	public void notifyCharacterAdded(BookCharacter bookCharacter) {
		for (BookCharacterObserver characterObserver : getObservers()) {
			characterObserver.characterAdded(bookCharacter);
		}
	}

	/**
	 * Notifie qu'un personnage a été supprimé
	 * @param bookCharacter Le personnage supprimé
	 */
	public void notifyCharacterDeleted(BookCharacter bookCharacter) {
		for (BookCharacterObserver characterObserver : getObservers()) {
			characterObserver.characterDeleted(bookCharacter);
		}
	}

	/**
	 * Notifie qu'un personnage a été mis à jour
	 * @param oldCharacter L'ancien personnage
	 * @param newCharacter Le nouveau personnage
	 */
	public void notifyCharacterEdited(BookCharacter oldCharacter, BookCharacter newCharacter) {
		for (BookCharacterObserver characterObserver : getObservers()) {
			characterObserver.characterEdited(oldCharacter, newCharacter);
		}
	}

}
