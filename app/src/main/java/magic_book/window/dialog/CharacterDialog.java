package magic_book.window.dialog;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;

import magic_book.core.game.BookCharacter;
import magic_book.window.component.CharacterComponent;

public class CharacterDialog extends AbstractDialog {
	
	private BookCharacter character;
	private CharacterComponent characterComponent;
	
	public CharacterDialog() {
		super("Ajout d'un personnage");
		
		this.showAndWait();
	}
	
	public CharacterDialog(BookCharacter character) {
		super("Edition de " + character.getName());
		
		this.characterComponent.setCharacter(character);
		
		this.showAndWait();
	}

	@Override
	protected Node getMainUI() {
		characterComponent = new CharacterComponent();
		return characterComponent;
	}

	@Override
	protected EventHandler<ActionEvent> getValidButtonEventHandler() {
		return (ActionEvent e) -> {
			BookCharacter character = characterComponent.getCharacter();
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
