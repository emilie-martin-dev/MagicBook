package magic_book.window.dialog;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import magic_book.core.game.BookCharacter;


public class CharacterDialog extends AbstractDialog {
	
	private BookCharacter character;
	
	private static final String PRINCIPAL = "Principal";
	private static final String ENNEMIES = "Ennemies";
	private static final String AMIS = "Amis";
	private static final String AUTRE = "Autre";
	
	private TextField idTextField;
	private TextField nameTextField;
	private TextField raceTextField;
	private TextField typePersoTextField;
	
	private ChoiceBox persoType;
	
	public CharacterDialog() {
		
		super("Ajout d'un personnage");
		
		this.showAndWait();
	}
	
	public CharacterDialog(BookCharacter character) {
		super("Edition de " + character.getName());
		
		idTextField.setText(character.getId());
		nameTextField.setText(character.getName());
		
		this.character = character;
		
		this.showAndWait();
	}

	@Override
	protected Node getMainUI() {
		GridPane root = new GridPane();
			
		
		persoType = new ChoiceBox<>();

 		persoType.getItems().add(PRINCIPAL);
 		persoType.getItems().add(ENNEMIES);
 		persoType.getItems().add(AMIS);
 		persoType.setValue(AUTRE);
		
		root.setHgap(10);
		root.setVgap(10);
		
		Label idLabel = new Label("Id : ");
		Label nameLabel = new Label("Name: ");
		Label typeLabel = new Label("Type de personnage : ");
		Label raceLabel = new Label("Race : ");
		
		idTextField = new TextField();
		nameTextField = new TextField();
		raceTextField = new TextField();
		
		root.add(idLabel, 0, 0);
		root.add(idTextField, 1, 0);
		root.add(nameLabel, 0, 1);
		root.add(nameTextField, 1, 1);
	/*	root.add(typeLabel, 0, 2);
		root.add(persoType, 1, 2);
		root.add(raceLabel, 0, 3);
		root.add(raceTextField, 1, 3);*/
		
		return root;
	}

	@Override
	protected EventHandler<ActionEvent> getValidButtonEventHandler() {
		return (ActionEvent e) -> {
			if (idTextField.getText().trim().isEmpty()
					|| nameTextField.getText().trim().isEmpty()) {
				return;
			}
			
			if(CharacterDialog.this.character == null)
				CharacterDialog.this.character = new BookCharacter(idTextField.getText().trim(), nameTextField.getText().trim(), 0, 0, null, null, 0);
			else {
				CharacterDialog.this.character.setId(idTextField.getText().trim());
				CharacterDialog.this.character.setName(nameTextField.getText().trim());
			}
			
			close();
		};
	}
	
	public BookCharacter getCharacter() {
		return character;
	}
	
}
