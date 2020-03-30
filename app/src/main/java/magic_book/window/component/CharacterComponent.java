package magic_book.window.component;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import magic_book.core.game.BookCharacter;

public class CharacterComponent extends GridPane {
	
	private TextField idTextField;
	private TextField nameTextField;
	private TextField hpTextField;
	private TextField combatSkillTextField;
	private CheckBox doubleDamageCheckBox;
	
	public CharacterComponent() {		
		this.setHgap(5);
		this.setVgap(5);
		
		Label idLabel = new Label("Id : ");
		Label nameLabel = new Label("Name: ");
		Label hpLabel = new Label("Hp : ");
		Label combatSkillLabel = new Label("Dégats : ");
		
		idTextField = new TextField();
		nameTextField = new TextField();
		hpTextField = new TextField();
		combatSkillTextField = new TextField();
		doubleDamageCheckBox = new CheckBox("Double dégats");
		
		this.add(idLabel, 0, 0);
		this.add(idTextField, 1, 0);
		this.add(nameLabel, 0, 1);
		this.add(nameTextField, 1, 1);
		this.add(hpLabel, 0, 2);
		this.add(hpTextField, 1, 2);
		this.add(combatSkillLabel, 0, 3);
		this.add(combatSkillTextField, 1, 3);
		this.add(doubleDamageCheckBox, 0, 4, 2, 1);
	}
	
	public BookCharacter getCharacter() {
			if (idTextField.getText().trim().isEmpty()
					|| nameTextField.getText().trim().isEmpty()) {
				return null;
			}
			
			int hp = 0;
			int damage = 0;
			try {
				hp = Integer.valueOf(hpTextField.getText());
				damage = Integer.valueOf(combatSkillTextField.getText());
			} catch(NumberFormatException ex) {
				return null;
			}
			
			if(hp < -1 || damage < -1)
				return null;
			
			BookCharacter character = new BookCharacter();
			
			character.setId(idTextField.getText().trim());
			character.setName(nameTextField.getText().trim());
			character.setHpMax(hp);
			character.setBaseDamage(damage);
			character.setDoubleDamage(doubleDamageCheckBox.isSelected());
			
			return character;
	}
	
	public void setCharacter(BookCharacter character) {
		if(character != null) {
			idTextField.setText(character.getId());
			nameTextField.setText(character.getName());
			hpTextField.setText(""+character.getHpMax());
			combatSkillTextField.setText(""+character.getBaseDamage());
			doubleDamageCheckBox.setSelected(character.isDoubleDamage());
		} else {
			idTextField.setText("");
			nameTextField.setText("");
			hpTextField.setText("");
			combatSkillTextField.setText("");
			doubleDamageCheckBox.setSelected(false);
		}
	}

}
