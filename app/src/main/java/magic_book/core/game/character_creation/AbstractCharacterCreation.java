package magic_book.core.game.character_creation;

import magic_book.core.Book;
import magic_book.core.parser.Descriptible;


public abstract class AbstractCharacterCreation implements Descriptible {

	private String text;

	public AbstractCharacterCreation(String text) {
		this.text = text;
	}
	
	public String getDescription(Book book) {
		return this.text + "\n";
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
