package magic_book.window.dialog;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import magic_book.core.Book;

import magic_book.core.game.BookCharacter;
import magic_book.window.component.CharacterComponent;

public class CharacterDialog extends AbstractDialog {
	
	private BookCharacter character;
	private CharacterComponent characterComponent;
	private Book book;
	
	public CharacterDialog(Book book) {
		super("Ajout d'un personnage");
		this.book = book;
		
		this.showAndWait();
	}
	
	public CharacterDialog(BookCharacter character, Book book) {
		super("Edition de " + character.getName());
		
		this.characterComponent.setCharacter(character);
		
		this.book = book;
	
		this.showAndWait();
	}

	@Override
	protected Node getMainUI() {
		characterComponent = new CharacterComponent(book);
		return characterComponent;
	}

	@Override
	protected EventHandler<ActionEvent> getValidButtonEventHandler() {
		return (ActionEvent e) -> {
			BookCharacter character = characterComponent.getCharacter(book);
			if(character == null)
				return;
			
			this.character = character;
			close();
		};
	}
	
	public BookCharacter getCharacter() {
		return character;
	}
	
}
