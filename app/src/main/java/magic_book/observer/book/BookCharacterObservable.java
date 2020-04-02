package magic_book.observer.book;

import magic_book.core.game.BookCharacter;
import magic_book.observer.Observable;

public class BookCharacterObservable extends Observable<BookCharacterObserver> {

	public void notifyCharacterAdded(BookCharacter bookCharacter) {
		for (BookCharacterObserver characterObserver : getObservers()) {
			characterObserver.characterAdded(bookCharacter);
		}
	}

	public void notifyCharacterDeleted(BookCharacter bookCharacter) {
		for (BookCharacterObserver characterObserver : getObservers()) {
			characterObserver.characterDeleted(bookCharacter);
		}
	}

	public void notifyCharacterEdited(BookCharacter oldCharacter, BookCharacter newCharacter) {
		for (BookCharacterObserver characterObserver : getObservers()) {
			characterObserver.characterEdited(oldCharacter, newCharacter);
		}
	}

}
