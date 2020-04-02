package magic_book.window.dialog;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import magic_book.core.Book;

import magic_book.core.game.BookCharacter;
import magic_book.window.component.CharacterComponent;

/**
 * Boite de dialog pour l'ajout des personnages
 */
public class CharacterDialog extends AbstractDialog {
	
	/**
	 * Personnage créer
	 */
	private BookCharacter character;
	/**
	 * Pane comprenant toutes les informations remplis pour la création du personnage principal
	 */
	private CharacterComponent characterComponent;
	/**
	 * Livre contenant toutes les informations
	 */
	private Book book;
	
	/**
	 * Initialisation de la boite de dialog du personnage
	 * @param book Le livre contenant toutes les informations
	 */
	public CharacterDialog(Book book) {
		super("Ajout d'un personnage");
		this.book = book;
		
		this.showAndWait();
	}
	
	/**
	 * Edition de la boite de dialog du personnage
	 * @param character Personnage existant
	 * @param book Le livre contenant toutes les informations
	 */
	public CharacterDialog(BookCharacter character, Book book) {
		super("Edition de " + character.getName());
		
		this.characterComponent.setCharacter(character);
		
		this.book = book;
	
		this.showAndWait();
	}

	@Override
	protected Node getMainUI() {
		characterComponent = new CharacterComponent(book, false);
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
	
	/**
	 * Donne le personnage
	 * @return Le personnage
	 */
	public BookCharacter getCharacter() {
		return character;
	}
	
}
