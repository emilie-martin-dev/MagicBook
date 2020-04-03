package magic_book.window.component;

import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import magic_book.core.Book;
import magic_book.core.game.BookCharacter;
import magic_book.window.UiConsts;

/**
 * Pane permettant la création d'un personnage
 */
public class CharacterComponent extends GridPane {
	
	/**
	 * ID du personnage
	 */
	private TextField idTextField;
	/**
	 * Nom du personnage
	 */
	private TextField nameTextField;
	/**
	 * Vie du personnage
	 */
	private TextField hpTextField;
	/**
	 * Dégat du personnage
	 */
	private TextField combatSkillTextField;
	/**
	 * Item maximum
	 */
	private TextField itemMaxTextField;
	/**
	 * Argent disponible
	 */
	private TextField argentTextField;
	/**
	 * Double domage activé ou non
	 */
	private CheckBox doubleDamageCheckBox;
	
	/**
	 * Livre contenant toutes les informations
	 */
	private Book book;
	/**
	 * Création du personnage principal ou non
	 */
	private Boolean mainCharacter;
	/**
	 * Base ID
	 */
	private String baseId = "";
	
	/**
	 * Initialisation du Pane de la création du personnage
	 * @param book Livre contenant toutes les informations
	 * @param mainCharacter Création du personnage principal (true) ou personnage autre (false)
	 */
	public CharacterComponent(Book book, Boolean mainCharacter) {		
		this.setHgap(UiConsts.DEFAULT_MARGIN);
		this.setVgap(UiConsts.DEFAULT_MARGIN);
		
		Label idLabel = new Label("Id : ");
		Label nameLabel = new Label("Name: ");
		Label hpLabel = new Label("Hp : ");
		Label combatSkillLabel = new Label("Dégats : ");
		Label itemMaxLabel = new Label("Item max : ");
		Label argentLabel = new Label("Argent : ");
		
		idTextField = new TextField();
		nameTextField = new TextField();
		hpTextField = new TextField();
		combatSkillTextField = new TextField();
		doubleDamageCheckBox = new CheckBox("Double dégats");
		itemMaxTextField = new TextField();
		argentTextField = new TextField();
		
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
		
		//Si c'est le personnage principal
		if(mainCharacter){
			this.add(itemMaxLabel, 0, 5);
			this.add(itemMaxTextField, 1, 5);
			this.add(argentLabel, 0, 6);
			this.add(argentTextField, 1, 6);
		}
	}
	
	/**
	 * Donne toutes les informations saisie
	 * @param book Livre contenant toutes les informations
	 * @return Le personnage créer
	 */
	public BookCharacter getCharacter(Book book) {
		//Si l'id et le name non pas de valeur de saisie
		if (idTextField.getText().trim().isEmpty()
				|| nameTextField.getText().trim().isEmpty()) {
			return null;
		}
		
		if(book != null){
			//Permet de ne pas utiliser id pour le personnage principal
			if (idTextField.getText().trim().equals(Book.MAIN_CHARACTER_ID)){
				errorIdAlreadyUsed();
				return null;
			}
			
			//Permet de ne pas avoir un id déjà utilisé
			if(book.getCharacters().containsKey(idTextField.getText()) && !baseId.equals(idTextField.getText())) {
				errorIdAlreadyUsed();
				return null;
			}
		}
		
		int hp;
		int damage;
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
		
		//Si c'est le personnage principal
		if(mainCharacter){
			int itemMaxInt;
			int argentInt;
			try {
				itemMaxInt = Integer.parseInt(itemMaxTextField.getText());
				argentInt = Integer.parseInt(argentTextField.getText());
			} catch (NumberFormatException ex){
				showErrorDialog(ex.getMessage());
				return null;
			}
			character.setItemsMax(itemMaxInt);
			character.changeMoneyAmount("gold", argentInt);
		}
			

		return character;
	}
	
	/**
	 * Permet de modifier le Pane actuel pour une édition des valeurs saisie
	 * @param character Personnage existant
	 */
	public void setCharacter(BookCharacter character) {
		if(character != null) {
			idTextField.setText(character.getId());
			nameTextField.setText(character.getName());
			hpTextField.setText(""+character.getHpMax());
			combatSkillTextField.setText(""+character.getBaseDamage());
			doubleDamageCheckBox.setSelected(character.isDoubleDamage());
			itemMaxTextField.setText(String.valueOf(character.getItemsMax()));
			baseId = character.getId();
			argentTextField.setText(""+character.getMoney("gold"));
		} else {
			idTextField.setText("");
			nameTextField.setText("");
			hpTextField.setText("");
			argentTextField.setText("");
			itemMaxTextField.setText("");
			combatSkillTextField.setText("");
			doubleDamageCheckBox.setSelected(false);
		}
	}

	/**
	 * Alerte si l'ID est déjà utilisé
	 */
	private void errorIdAlreadyUsed(){
		Alert alertDialog = new Alert(Alert.AlertType.ERROR);
		
		alertDialog.setTitle("Erreur");
		alertDialog.setHeaderText("L'ID n'est pas disponible");
		alertDialog.show();
	}
	
	/**
	 * Alert erreur
	 * @param ex Exception d'erreur
	 */
	private void showErrorDialog(String message){
		Alert alertDialog = new Alert(Alert.AlertType.ERROR);
		
		alertDialog.setTitle("Erreur");
		alertDialog.setHeaderText(message);
		alertDialog.show();
	}
}
