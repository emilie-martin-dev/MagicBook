package magic_book.window.dialog;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import magic_book.core.BookCharacter;

import javafx.stage.Stage;

public class CharacterDialog extends Stage {
	
	private BookCharacter character;
	
	private TextField idTextField;
	private TextField nameTextField;
	private TextField raceTextField;
	
	public CharacterDialog() {
		initStageUI("Ajout d'un personnage");
		
		this.showAndWait();
	}
	
	public CharacterDialog(BookCharacter character) {
		initStageUI("Edition de " + character.getName());
		
		idTextField.setText(character.getId());
		nameTextField.setText(character.getName());
		raceTextField.setText(character.getRace());
		
		this.showAndWait();
	}
	
	private void initStageUI(String title) {
		GridPane root = new GridPane();
		
		root.setPadding(new Insets(25));
		root.setHgap(10);
		root.setVgap(10);
		
		Label idLabel = new Label("Id : ");
		Label nameLabel = new Label("Name: ");
		Label raceLabel = new Label("Race : ");
		
		idTextField = new TextField();
		nameTextField = new TextField();
		raceTextField = new TextField();
		
		Button boutonAnnuler = new Button("Annuler");
		boutonAnnuler.setOnAction((ActionEvent e) -> {
			close();
		});
		
		Button boutonValider = new Button("Valider");
		boutonValider.setOnAction((ActionEvent e) -> {
			if (idTextField.getText().trim().isEmpty()
					|| nameTextField.getText().trim().isEmpty()
					|| raceTextField.getText().trim().isEmpty()) {
				return;
			}
			
			CharacterDialog.this.character = new BookCharacter(idTextField.getText().trim(), nameTextField.getText().trim(), raceTextField.getText().trim(), 0, 0, null, null, 0);
			
			close();
		});
		
		HBox buttonsBox = new HBox();
		buttonsBox.setSpacing(10d);
		buttonsBox.setAlignment(Pos.BASELINE_RIGHT);
		buttonsBox.getChildren().add(boutonAnnuler);
		buttonsBox.getChildren().add(boutonValider);
		
		root.add(idLabel, 0, 0);
		root.add(idTextField, 1, 0);
		root.add(nameLabel, 0, 1);
		root.add(nameTextField, 1, 1);
		root.add(raceLabel, 0, 2);
		root.add(raceTextField, 1, 2);
		root.add(buttonsBox, 0, 3, 2, 1);
		
		Scene scene = new Scene(root);
		
		this.setResizable(false);
		this.initModality(Modality.APPLICATION_MODAL);
		this.setScene(scene);
		this.setTitle(title);
	}
	
	public BookCharacter getCharacter() {
		return character;
	}
	
}
