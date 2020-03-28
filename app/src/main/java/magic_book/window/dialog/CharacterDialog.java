package magic_book.window.dialog;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
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
	private TextField hpTextField;
	private TextField combatSkillTextField;
	private CheckBox doubleDamageCheckBox;
	
	private ChoiceBox persoType;
	
	public CharacterDialog() {
		
		super("Ajout d'un personnage");
		
		this.showAndWait();
	}
	
	public CharacterDialog(BookCharacter character) {
		super("Edition de " + character.getName());
		
		idTextField.setText(character.getId());
		nameTextField.setText(character.getName());
		hpTextField.setText(""+character.getHpMax());
		combatSkillTextField.setText(""+character.getBaseDamage());
		doubleDamageCheckBox.setSelected(character.isDoubleDamage());
		
		this.showAndWait();
	}

	@Override
	protected Node getMainUI() {
		GridPane root = new GridPane();
			
		
		persoType = new ChoiceBox<>();
		
		root.setHgap(5);
		root.setVgap(5);
		
		Label idLabel = new Label("Id : ");
		Label nameLabel = new Label("Name: ");
		Label hpLabel = new Label("Hp : ");
		Label combatSkillLabel = new Label("Dégats : ");
		
		idTextField = new TextField();
		nameTextField = new TextField();
		hpTextField = new TextField();
		combatSkillTextField = new TextField();
		doubleDamageCheckBox = new CheckBox("Double dégats");
		
		Label typeLabel = new Label("Type de personnage : ");
		Label raceLabel = new Label("Race : ");
		
		idTextField = new TextField();
		nameTextField = new TextField();
		
		root.add(idLabel, 0, 0);
		root.add(idTextField, 1, 0);
		root.add(nameLabel, 0, 1);
		root.add(nameTextField, 1, 1);
		
		root.add(hpLabel, 0, 2);
		root.add(hpTextField, 1, 2);
		root.add(combatSkillLabel, 0, 3);
		root.add(combatSkillTextField, 1, 3);
		root.add(doubleDamageCheckBox, 0, 4, 2, 1);
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
			
			int hp = 0;
			int damage = 0;
			try {
				hp = Integer.valueOf(hpTextField.getText());
				damage = Integer.valueOf(combatSkillTextField.getText());
			} catch(NumberFormatException ex) {
				return;
			}
			
			if(hp < -1 || damage < -1)
				return;
			
			CharacterDialog.this.character = new BookCharacter();
			
			CharacterDialog.this.character.setId(idTextField.getText().trim());
			CharacterDialog.this.character.setName(nameTextField.getText().trim());
			CharacterDialog.this.character.setHpMax(hp);
			CharacterDialog.this.character.setBaseDamage(damage);
			CharacterDialog.this.character.setDoubleDamage(doubleDamageCheckBox.isSelected());
			
			close();
		};
	}
	
	public BookCharacter getCharacter() {
		return character;
	}
	
}
