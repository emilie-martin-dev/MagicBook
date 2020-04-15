package magic_book.window.dialog;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import magic_book.core.Book;
import magic_book.core.game.BookSkill;
import magic_book.window.UiConsts;

/**
 * Boite de dialog pour la gestion des skills
 */
public class SkillDialog extends AbstractDialog {
	
	/**
	 * Skill créer
	 */
	private BookSkill skill;
	
	/**
	 * Id du skill
	 */
	private TextField idTextField;
	/**
	 * Nom du skill
	 */
	private TextField nameTextField;
	
	/**
	 * Livre contenant toutes les informations
	 */
	private Book book;
	
	/**
	 * L'id du skill en cours d'édition
	 */
	private String baseId;
	
	/**
	 * Initialisation des valeurs et de la fenêtre de dialog
	 * @param book Le livre contenant toutes les informations
	 */
	public SkillDialog(Book book) {
		super("Ajout d'un skill");

		this.book = book;
		this.showAndWait();
	}

	/**
	 * Constructeur
	 * @param skill Skill existant
	 * @param book Le livre contenant toutes les informations
	 */
	public SkillDialog(BookSkill skill, Book book) {
		super("Edition de " + skill.getName());
			
		idTextField.setText(skill.getId());
		nameTextField.setText(skill.getName());
		
		this.book = book;
		this.baseId = skill.getId();
		
		this.showAndWait();
	}

	@Override
	protected Node getMainUI() {
		GridPane root = new GridPane();
		
		root.setHgap(UiConsts.DEFAULT_MARGIN);
		root.setVgap(UiConsts.DEFAULT_MARGIN);
		
		Label idLabel = new Label("Id : ");
		Label nameLabel = new Label("Name: ");

		idTextField = new TextField("");
		nameTextField = new TextField("");
		
		root.add(idLabel, 0, 0);
		root.add(idTextField, 1, 0);
		root.add(nameLabel, 0, 1);
		root.add(nameTextField, 1, 1);
		
		return root;
	}

	@Override
	protected EventHandler<ActionEvent> getValidButtonEventHandler() {
		return (ActionEvent e) -> {
			//Ne valide pas si toute les valeurs ne sont pas saisie
			if (idTextField.getText().trim().isEmpty()
					|| nameTextField.getText().trim().isEmpty()){
				return;
			}

			//Ne valide pas si l'ID est déjà pris'
			if(book != null){
				if(book.getSkills().containsKey(idTextField.getText().trim()) && !baseId.equals(idTextField.getText().trim())){
					showErrorDialog("L'ID n'est pas disponible");
					return;
				}
			}
		
			this.skill = new BookSkill();
			this.skill.setId(idTextField.getText().trim());
			this.skill.setName(nameTextField.getText().trim());
			
			close();
		};
	}
	
	/**
	 * Afficher une boite d'alerte si l'ID est déjà utilisé
	 * @param message Message à afficher
	 */
	private void showErrorDialog(String message){
		Alert alertDialog = new Alert(Alert.AlertType.ERROR);
		
		alertDialog.setTitle("Erreur");
		alertDialog.setHeaderText(message);
		alertDialog.show();
	}

	/**
	 * Retourne le skill
	 * @return Le skill
	 */
	public BookSkill getSkill() {
		return skill;
	}

}
