package magic_book.observer.book;

import magic_book.core.game.BookCharacter;

public interface BookCharacterObserver {

	public void characterAdded(BookCharacter character);

	public void characterEdited(BookCharacter oldCharacter, BookCharacter newCharacter);

	public void characterDeleted(BookCharacter character);
	
}
