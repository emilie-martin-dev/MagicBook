package magic_book.window.dialog;

import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import magic_book.core.Book;
import magic_book.core.game.BookCharacter;
import magic_book.core.game.character_creation.AbstractCharacterCreation;
import magic_book.window.UiConsts;
import magic_book.window.component.CharacterComponent;
import magic_book.window.component.CharacterCreationComponent;

/**
 * Boite de dialog pour la création / édition du prélude, du personnage principal et de la "Création du personnage"
 */
public class PreludeDialog extends AbstractDialog {
	
	/**
	 * Liste de toute les étapes de la création du personnage
	 */
	private List<AbstractCharacterCreation> characterCreations;
	/**
	 * Personnage principal
	 */
	private BookCharacter mainCharacter;
	/**
	 * Texte du prélude
	 */
	private String textePrelude;
	
	/**
	 * TextArea contenant le texte du prélude
	 */
	private TextArea texte;
	/**
	 * Contient les différentes étapes de la création du personnage
	 */
	private Accordion accordion;
	/**
	 * Pane comprenant toutes les informations sur le personnage principal
	 */
	private CharacterComponent characterComponent;
	/**
	 * Comporte tous les Pane des étapes de la création d'un personnage
	 */
	private List<CharacterCreationComponent> characterCreationComponent;
	
	/**
	 * Livre en cours d'édition
	 */
	private Book book;

	/**
	 * Initialisation du prélude
	 * @param book Le livre en cours d'édition
	 */
	public PreludeDialog(Book book) {
		super("Edition du Prelude", true);

		this.book = book;
		this.characterCreationComponent = new ArrayList<>();
		
		texte.setText(book.getTextPrelude());
		characterComponent.setCharacter(book.getMainCharacter());
		
		for(AbstractCharacterCreation characterCreation : book.getCharacterCreations()) {
			createTitledPane(characterCreation);
		}
		
		this.showAndWait();
	}
	
	@Override
	protected Node getMainUI() {
		TabPane tabPane = new TabPane();
		
		Tab preludeTab = new Tab("Prélude");
		Tab characterCreationTab = new Tab("Création du personnage");
		Tab mainCharacterTab = new Tab("Personnage de base");
		
		preludeTab.setClosable(false);
		characterCreationTab.setClosable(false);
		mainCharacterTab.setClosable(false);

		tabPane.getTabs().addAll(preludeTab, characterCreationTab, mainCharacterTab);
		
		preludeTab.setContent(getPreludeTextPane());
		characterCreationTab.setContent(getCharacterCreationTab());
		mainCharacterTab.setContent(getMainCharacterPane());
		
		return tabPane;
	}
	
	/**
	 * Affichage pour le texte du prélude
	 * @return Le pane du texte du prélude
	 */
	private Node getPreludeTextPane() {
		GridPane preludeRoot = new GridPane();
		preludeRoot.setHgap(UiConsts.DEFAULT_MARGIN);
		preludeRoot.setVgap(UiConsts.DEFAULT_MARGIN);
		preludeRoot.setPadding(UiConsts.DEFAULT_INSET_DIALOG_MAIN_UI);
		
		texte = new TextArea();
		texte.setWrapText(true);
		preludeRoot.add(new Label("Texte :"), 0, 0);
		preludeRoot.add(texte, 0, 1);
		
		return preludeRoot;
	}
	
	/**
	 * Affichage pour l'ajout des étapes de la creation du personnage
	 * @return Le pane de la création étapes de la creation du personnage
	 */
	private Node getCharacterCreationTab() {
		VBox characterCreationPane = new VBox();
		characterCreationPane.setSpacing(UiConsts.DEFAULT_MARGIN);
		
		accordion = new Accordion();
		accordion.setPadding(UiConsts.DEFAULT_INSET_DIALOG_MAIN_UI);
		
		Button addButton = new Button("Ajouter");
		addButton.setOnAction((ActionEvent e) -> {
			createTitledPane();
		});
		
		VBox buttonBox = new VBox();
		buttonBox.setAlignment(Pos.CENTER_RIGHT);
		buttonBox.getChildren().add(addButton);
		buttonBox.setPadding(new Insets(0, UiConsts.DEFAULT_MARGIN_DIALOG, 0, 0));
		
		characterCreationPane.getChildren().addAll(accordion, buttonBox);
		
		ScrollPane scrollPane = new ScrollPane(characterCreationPane);
		scrollPane.setFitToWidth(true);
		
		return scrollPane;
	}
	
	/**
	 * Méthode pour créer un nouvel élément de la conception du personnage
	 */
	private void createTitledPane() {
		createTitledPane(null);
	}
	
	/**
	 * Méthode pour créer un nouvel élément de la conception du personnage
	 * @param characterCreation L'élément de la conception du personnage
	 */
	private void createTitledPane(AbstractCharacterCreation characterCreation) {
		TitledPane titledPane = new TitledPane();
		
		CharacterCreationComponent characterCreationPane = new CharacterCreationComponent(book, characterCreation);
		Button remove = new Button("Supprimer cette partie");
		
		remove.setOnAction((ActionEvent e) -> {
			// Sans la ligne suivante, l'accordion ne se redimensionne pas après la suppression de la tiledPane
			titledPane.setContent(new HBox());
			accordion.getPanes().remove(titledPane);
			this.characterCreationComponent.remove(characterCreationPane);
		});
		
		HBox removeBox = new HBox();
		removeBox.getChildren().add(remove);
		removeBox.setAlignment(Pos.CENTER_RIGHT);
		
		VBox titledPaneBox = new VBox();
		titledPaneBox.getChildren().addAll(characterCreationPane, removeBox);
		titledPaneBox.setPadding(UiConsts.DEFAULT_INSET_DIALOG);
		
		titledPane.setContent(titledPaneBox);
		
		accordion.getPanes().add(titledPane);
		this.characterCreationComponent.add(characterCreationPane);
	}
	
	/**
	 * Affichage pour la création du personnage principal
	 * @return personnage principal
	 */
	private Node getMainCharacterPane() {
		characterComponent = new CharacterComponent(null, true);
		characterComponent.setAlignment(Pos.CENTER);
		
		return characterComponent;
	}

	@Override
	protected EventHandler<ActionEvent> getValidButtonEventHandler() {
		return (ActionEvent e) -> {
			BookCharacter character = characterComponent.getCharacter(null);
			if(character == null) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Erreur sur le personnage principal");
				alert.setHeaderText("Le personnage principal n'est pas valide");
				alert.show();
				
				return;
			}
			
			this.textePrelude = (String) texte.getText();
			this.mainCharacter = character;
			this.characterCreations = new ArrayList<>();
			for(CharacterCreationComponent characterCreationComponent : characterCreationComponent) {
				this.characterCreations.add(characterCreationComponent.getCharacterCreation());
			}
			
			close();
		};
	}

	/**
	 * Donne le texte du prélude
	 * @return Le texte du prélude
	 */
	public String getTextePrelude() {
		return textePrelude;
	}

	/**
	 * Donne la liste des étapes de la création du personnage principal
	 * @return La liste des étapes de la création du personnage principal
	 */
	public List<AbstractCharacterCreation> getCharacterCreations() {
		return characterCreations;
	}

	/**
	 * Donne le personnage principal créer
	 * @return Personnage principal
	 */
	public BookCharacter getMainCharacter() {
		return mainCharacter;
	}
}
