package magic_book.window.component;

import java.util.HashMap;
import java.util.Map.Entry;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import magic_book.core.Book;
import magic_book.core.game.BookCharacter;
import magic_book.window.UiConsts;

public class CharacterComponent extends GridPane {
	
	private TextField idTextField;
	private TextField nameTextField;
	private TextField hpTextField;
	private TextField combatSkillTextField;
	private TextField itemMaxTextField;
	private CheckBox doubleDamageCheckBox;
	
	private Label itemMaxLabel;
	
	private Book book;
	private Boolean mainCharacter;
	private String baseId = "";
	
	public CharacterComponent(Book book, Boolean mainCharacter) {		
		this.setHgap(UiConsts.DEFAULT_MARGIN);
		this.setVgap(UiConsts.DEFAULT_MARGIN);
		
		Label idLabel = new Label("Id : ");
		Label nameLabel = new Label("Name: ");
		Label hpLabel = new Label("Hp : ");
		Label combatSkillLabel = new Label("Dégats : ");
		Label itemMaxLabel = new Label("Item max : ");
		
		idTextField = new TextField();
		nameTextField = new TextField();
		hpTextField = new TextField();
		combatSkillTextField = new TextField();
		doubleDamageCheckBox = new CheckBox("Double dégats");
		itemMaxTextField = new TextField();
		
		this.add(idLabel, 0, 0);
		this.add(idTextField, 1, 0);
		this.add(nameLabel, 0, 1);
		this.add(nameTextField, 1, 1);
		this.add(hpLabel, 0, 2);
		this.add(hpTextField, 1, 2);
		this.add(combatSkillLabel, 0, 3);
		this.add(combatSkillTextField, 1, 3);
		this.add(doubleDamageCheckBox, 0, 4, 2, 1);
		
		this.book = book;
		this.mainCharacter = mainCharacter;
		
		if(mainCharacter){
			this.add(itemMaxLabel, 0, 5);
			this.add(itemMaxTextField, 1, 5);
		}
	}
	
	public BookCharacter getCharacter(Book book) {
		if (idTextField.getText().trim().isEmpty()
				|| nameTextField.getText().trim().isEmpty()
				|| itemMaxTextField.getText().isEmpty()) {
			return null;
		}
		
		if(book != null){
			if (idTextField.getText().trim().equals(Book.MAIN_CHARACTER_ID)){
				errorIdAlreadyUsed();
				return null;
			}
		
			if(book.getCharacters().containsKey(idTextField.getText()) && !baseId.equals(idTextField.getText())) {
				errorIdAlreadyUsed();
				return null;
			}
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
		if(mainCharacter){
			int itemMaxInt;
			try {
				itemMaxInt = Integer.parseInt(itemMaxTextField.getText());
			} catch (NumberFormatException ex){
				notANumberAlertDialog(ex.getMessage());
				return null;
			}
			character.setItemsMax(itemMaxInt);
		}
			

		return character;
	}
	
	public void setCharacter(BookCharacter character) {
		if(character != null) {
			idTextField.setText(character.getId());
			nameTextField.setText(character.getName());
			hpTextField.setText(""+character.getHpMax());
			combatSkillTextField.setText(""+character.getBaseDamage());
			doubleDamageCheckBox.setSelected(character.isDoubleDamage());
			itemMaxTextField.setText(String.valueOf(character.getItemsMax()));
			baseId = character.getId();
		} else {
			idTextField.setText("");
			nameTextField.setText("");
			hpTextField.setText("");
			combatSkillTextField.setText("");
			doubleDamageCheckBox.setSelected(false);
		}
	}

	private void errorIdAlreadyUsed(){
		Alert alertDialog = new Alert(Alert.AlertType.ERROR);
		
		alertDialog.setTitle("Erreur");
		alertDialog.setHeaderText("L'ID n'est pas disponible");
		alertDialog.show();
	}
	
	private void notANumberAlertDialog(String message){
		showErrorDialog(message.replace("For input string: ", "") + " n'est pas un entier");
	}

	private void showErrorDialog(String message){
		Alert alertDialog = new Alert(Alert.AlertType.ERROR);
		
		alertDialog.setTitle("Erreur");
		alertDialog.setHeaderText(message);
		alertDialog.show();
	}
}
