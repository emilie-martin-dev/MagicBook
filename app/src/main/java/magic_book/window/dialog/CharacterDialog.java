package magic_book.window.dialog;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import magic_book.core.game.BookCharacter;


public class CharacterDialog extends AbstractDialog {
	
	private BookCharacter character;
	
	private TextField idTextField;
	private TextField nameTextField;
	private TextField raceTextField;
	
	public CharacterDialog() {
		super("Ajout d'un personnage");
		
		this.showAndWait();
	}
	
	public CharacterDialog(BookCharacter character) {
		super("Edition de " + character.getName());
		
		idTextField.setText(character.getId());
		nameTextField.setText(character.getName());
		raceTextField.setText(character.getRace());
		
		this.showAndWait();
	}

	@Override
	protected Node getMainUI() {
		GridPane root = new GridPane();
		
		root.setHgap(10);
		root.setVgap(10);
		
		Label idLabel = new Label("Id : ");
		Label nameLabel = new Label("Name: ");
		Label raceLabel = new Label("Race : ");
		
		idTextField = new TextField();
		nameTextField = new TextField();
		raceTextField = new TextField();
		
		root.add(idLabel, 0, 0);
		root.add(idTextField, 1, 0);
		root.add(nameLabel, 0, 1);
		root.add(nameTextField, 1, 1);
		root.add(raceLabel, 0, 2);
		root.add(raceTextField, 1, 2);
		
		return root;
	}

	@Override
	protected EventHandler<ActionEvent> getValidButtonEventHandler() {
		return (ActionEvent e) -> {
			if (idTextField.getText().trim().isEmpty()
					|| nameTextField.getText().trim().isEmpty()
					|| raceTextField.getText().trim().isEmpty()) {
				return;
			}
			
			CharacterDialog.this.character = new BookCharacter(idTextField.getText().trim(), nameTextField.getText().trim(), raceTextField.getText().trim(), 0, 0, null, null, 0);
			
			close();
		};
	}
	
	public BookCharacter getCharacter() {
		return character;
	}
	
}
