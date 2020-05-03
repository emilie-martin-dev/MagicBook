package magic_book.window.component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import magic_book.core.Book;
import magic_book.core.game.BookSkill;
import magic_book.core.game.character_creation.AbstractCharacterCreation;
import magic_book.core.game.character_creation.CharacterCreationItem;
import magic_book.core.game.character_creation.CharacterCreationShop;
import magic_book.core.game.character_creation.CharacterCreationSkill;
import magic_book.core.game.character_creation.CharacterCreationText;
import magic_book.core.item.BookItemLink;
import magic_book.window.UiConsts;

/**
 * Permet de concevoir une étape de la "Création du personnage"
 */
public class CharacterCreationComponent extends GridPane {

	/**
	 * Type texte
	 */
	private static final String TYPE_TEXT = "Texte";

	/**
	 * Type skill
	 */
	private static final String TYPE_SKILL = "Compétence";

	/**
	 * Type item
	 */
	private static final String TYPE_ITEM = "Item";
	/**
	 * Type shop
	 */
	private static final String TYPE_SHOP = "Shop";

	/**
	 * Le type d'étape que l'on souhaite
	 */
	private ComboBox<String> characterCreationType;

	/**
	 * Pane de l'ajout d'item à prendre
	 */
	private ItemListComponent itemLinksList;

	/**
	 * Pane de l'ajout d'item à acheter
	 */
	private ItemListComponent shopLinksList;

	/**
	 * Pane de l'ajout de skill à apprendre
	 */
	private GridPane skillPane;

	/**
	 * Pane contenant les comboBox des skills
	 */
	private GridPane addSkillPane;

	/**
	 * Liste des ComboBox contenant les skills
	 */
	private List<ComboBox> skillComboBox;

	/**
	 * Texte juste après le prélude
	 */
	private TextArea texte;

	/**
	 * Livre contenant toutes les informations
	 */
	private Book book;

	/**
	 * Création d'un CharacterCreationComponent
	 * @param book Livre contenant toutes les informations
	 */
	public CharacterCreationComponent(Book book) {
		this(book, null);
		this.book = book;
		skillComboBox = new ArrayList();
	}

	/**
	 * Initialisation des valeurs du GridPane
	 * @param book Livre contenant toutes les informations
	 * @param abstractCharacterCreation
	 */
	public CharacterCreationComponent(Book book, AbstractCharacterCreation abstractCharacterCreation) {
		this.setHgap(UiConsts.DEFAULT_MARGIN);
		this.setVgap(UiConsts.DEFAULT_MARGIN);

		this.book = book;

		texte = new TextArea();
		texte.setWrapText(true);

		characterCreationType = new ComboBox<>();
		characterCreationType.getItems().add(TYPE_TEXT);
		characterCreationType.setValue(TYPE_TEXT);

		if(!book.getItems().isEmpty()){
			characterCreationType.getItems().add(TYPE_ITEM);
			characterCreationType.getItems().add(TYPE_SHOP);
		}
		if(!book.getSkills().isEmpty()){
			characterCreationType.getItems().add(TYPE_SKILL);
		}

		this.add(new Label("Texte :"), 0, 0);
		this.add(texte, 0, 1, 2, 1);
		this.add(new Label("Type"), 0, 2);
		this.add(characterCreationType, 1, 2);

		itemLinksList = new ItemListComponent(book, false);
		shopLinksList = new ItemListComponent(book, true);
		skillComboBox = new ArrayList();

		characterCreationType.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> ov, String t, String t1) -> {
			this.getChildren().remove(itemLinksList);
			this.getChildren().remove(shopLinksList);
			this.getChildren().remove(skillPane);

			if(characterCreationType.getValue().equals(TYPE_ITEM))
				addItemLinksComponent();

			if(characterCreationType.getValue().equals(TYPE_SHOP))
				addShopLinksComponent();

			if(characterCreationType.getValue().equals(TYPE_SKILL))
				addSkillLinksComponent();
		});

		if(abstractCharacterCreation != null)
			setCharacterCreation(abstractCharacterCreation);
	}

	/**
	 * Affiche la liste des items que l'on peut prendre
	 */
	private void addItemLinksComponent() {
		this.add(itemLinksList, 0, 3, 2, 1);
	}

	/**
	 * Affiche la liste des items que l'on peut acheter
	 */
	private void addShopLinksComponent() {
		this.add(shopLinksList, 0, 3, 2, 1);
	}

	/**
	 * Affiche la liste des skills que l'on peut apprendre
	 */
	private void addSkillLinksComponent() {
		skillPane = new GridPane();
		skillPane.setVgap(UiConsts.DEFAULT_MARGIN);
		skillPane.setHgap(UiConsts.DEFAULT_MARGIN);

		addSkillPane = new GridPane();
		addSkillPane.setVgap(UiConsts.DEFAULT_MARGIN);
		addSkillPane.setHgap(UiConsts.DEFAULT_MARGIN);

		Button addSkill = new Button("Ajouter un skill");
		addSkill.setOnAction((ActionEvent e) -> {
			addSkillComboBox();
		});

		skillPane.add(addSkill, 0, 1);
		skillPane.add(addSkillPane, 0, 2);

		this.add(skillPane, 0, 3);
	}

	/**
	 * Ajoute une comboBox contenant les skills
	 * @return ComboBox contenant les skills
	 */
	private ComboBox<String> addSkillComboBox(){
		ComboBox<String> comboSkills = new ComboBox<>();
		comboSkills.getItems().add(" ");
		comboSkills.setValue(" ");
		
		for(Map.Entry<String, BookSkill> listSkillBook : this.book.getSkills().entrySet()){
			comboSkills.getItems().add(listSkillBook.getValue().getId());
		}

		addSkillPane.add(comboSkills, skillComboBox.size() % 4, skillComboBox.size() / 4);

		skillComboBox.add(comboSkills);
		return comboSkills;
	}

	/**
	 * Retourne l'étape de la création du personnage
	 * @return Étape de la création du personnage
	 */
	public AbstractCharacterCreation getCharacterCreation() {
		AbstractCharacterCreation characterCreation = null;

		if(characterCreationType.getValue() == TYPE_ITEM) {
			CharacterCreationItem characterCreationItem = new CharacterCreationItem();
			characterCreationItem.setItemLinks(itemLinksList.getBookItemLinks());

			characterCreation = characterCreationItem;
		} else if(characterCreationType.getValue() == TYPE_SHOP) {
			CharacterCreationShop characterCreationShop = new CharacterCreationShop();
			characterCreationShop.setItemShopLinks(shopLinksList.getBookItemLinks());

			characterCreation = characterCreationShop;
		} else if(characterCreationType.getValue() == TYPE_SKILL) {
			List<String> listSkill = new ArrayList();
			for(ComboBox<String> skillBox : skillComboBox){
				if(!skillBox.getValue().trim().isEmpty()){
					listSkill.add(skillBox.getValue());
				}
			}
			CharacterCreationSkill characterCreationSkill = new CharacterCreationSkill();
			characterCreationSkill.setSkillLinks(listSkill);

			characterCreation = characterCreationSkill;
		} else if(characterCreationType.getValue() == TYPE_TEXT) {
			characterCreation = new CharacterCreationText();
		}

		characterCreation.setText(texte.getText());

		return characterCreation;
	}

	/**
	 * Change l'étape à éditer
	 * @param characterCreation étape à éditer
	 */
	public void setCharacterCreation(AbstractCharacterCreation characterCreation) {
		if(characterCreation instanceof CharacterCreationItem) {
			CharacterCreationItem characterCreationItem = (CharacterCreationItem) characterCreation;
			itemLinksList.setBookItemLinks(characterCreationItem.getItemLinks());
			characterCreationType.setValue(TYPE_ITEM);
		} else if(characterCreation instanceof CharacterCreationShop) {
			CharacterCreationShop characterCreationShop = (CharacterCreationShop) characterCreation;
			shopLinksList.setBookItemLinks(characterCreationShop.getItemShopLinks());
			characterCreationType.setValue(TYPE_SHOP);
		} else if(characterCreation instanceof CharacterCreationSkill) {
			characterCreationType.setValue(TYPE_SKILL);
			skillComboBox.clear();
			CharacterCreationSkill characterCreationSkill = (CharacterCreationSkill) characterCreation;
			for(String idSkill : characterCreationSkill.getSkillLinks()){
				ComboBox skillBox = addSkillComboBox();
				skillBox.setValue(idSkill);
			}
		}

		texte.setText(characterCreation.getText());
	}

}
