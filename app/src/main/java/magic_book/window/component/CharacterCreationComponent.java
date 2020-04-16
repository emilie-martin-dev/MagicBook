package magic_book.window.component;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import magic_book.core.Book;
import magic_book.core.game.character_creation.AbstractCharacterCreation;
import magic_book.core.game.character_creation.CharacterCreationItem;
import magic_book.core.game.character_creation.CharacterCreationText;
import magic_book.window.UiConsts;

/**
 * Permet de concevoir une étape de la "Création du personnage"
 */
public class CharacterCreationComponent extends GridPane {
		
	/**
	 * Type texte
	 */
	private static final String TYPE_TEXT = "Texte";
	//private static final String TYPE_SKILL = "Skills";
	/**
	 * Type item
	 */
	private static final String TYPE_ITEM = "Item";

	/**
	 * Le type d'étape que l'on souhaite
	 */
	private ComboBox<String> characterCreationType;
	
	/**
	 * Pane de l'ajout d'item
	 */
	private ItemListComponent itemLinksList;
	/**
	 * Texte juste après le prélude
	 */
	private TextArea texte;

	/**
	 * Création d'un CharacterCreationComponent
	 * @param book Livre contenant toutes les informations
	 */
	public CharacterCreationComponent(Book book) {
		this(book, null);
	}
	
	/**
	 * Initialisation des valeurs du GridPane
	 * @param book Livre contenant toutes les informations
	 * @param abstractCharacterCreation 
	 */
	public CharacterCreationComponent(Book book, AbstractCharacterCreation abstractCharacterCreation) {
		this.setHgap(UiConsts.DEFAULT_MARGIN);
		this.setVgap(UiConsts.DEFAULT_MARGIN);

		texte = new TextArea();
		texte.setWrapText(true);

		characterCreationType = new ComboBox<>();
		characterCreationType.getItems().add(TYPE_TEXT);
		characterCreationType.setValue(TYPE_TEXT);
		
		if(!book.getItems().isEmpty())
			characterCreationType.getItems().add(TYPE_ITEM);

		this.add(new Label("Texte :"), 0, 0);
		this.add(texte, 0, 1, 2, 1);
		this.add(new Label("Type"), 0, 2);
		this.add(characterCreationType, 1, 2);
		
		itemLinksList = new ItemListComponent(book, true);

		characterCreationType.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> ov, String t, String t1) -> {
			this.getChildren().remove(itemLinksList);
			
			if(characterCreationType.getValue().equals(TYPE_ITEM)) {
				addItemLinksComponent();
			}
		});

		if(abstractCharacterCreation != null)
			setCharacterCreation(abstractCharacterCreation);
	}

	/**
	 * Permet d'afficher le Pane qui ajoute les items si jamais TYPE_ITEM est sélectionné dans la ChoiceBox
	 */
	private void addItemLinksComponent() {
		this.add(itemLinksList, 0, 3, 2, 1);
	}

	/**
	 * Permet de retourner l'étape de la création du personnage
	 * @return Étape de la création du personnage
	 */
	public AbstractCharacterCreation getCharacterCreation() {
		AbstractCharacterCreation characterCreation = null;

		if(characterCreationType.getValue() == TYPE_ITEM) {
			CharacterCreationItem characterCreationItem = new CharacterCreationItem();
			characterCreationItem.setItemLinks(itemLinksList.getBookItemLinks());

			characterCreation = characterCreationItem;
		} else if(characterCreationType.getValue() == TYPE_TEXT) {
			characterCreation = new CharacterCreationText();
		}

		characterCreation.setText(texte.getText());

		return characterCreation;
	}

	/**
	 * Permet de changer l'étape à éditer
	 * @param characterCreation étape à éiter
	 */
	public void setCharacterCreation(AbstractCharacterCreation characterCreation) {
		if(characterCreation instanceof CharacterCreationItem) {
			CharacterCreationItem characterCreationItem = (CharacterCreationItem) characterCreation;
			
			itemLinksList.setBookItemLinks(characterCreationItem.getItemLinks());
			characterCreationType.setValue(TYPE_ITEM);
		}

		texte.setText(characterCreation.getText());
	}

}