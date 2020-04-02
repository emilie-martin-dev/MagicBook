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
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
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
 * Boite de dialog pour la création/édition du prélude
 */
public class PreludeDialog extends AbstractDialog {
	
	/**
	 * Liste de tout les skills, items, textex à prendre/afficher après le prélude
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
	 * Contient le texte du personnage ainsi que les iteme disponible lors du prélude
	 */
	private Accordion accordion;
	/**
	 * Permet de sroller la fenêtre du prélude
	 */
	private ScrollPane scrollPane;
	/**
	 * Pane comprenant toutes les informations remplis pour la création du personnage principal
	 */
	private CharacterComponent characterComponent;
	/**
	 * Comporte tout les textes de début et les items liés à ces textes
	 */
	private List<CharacterCreationComponent> characterCreationComponent;
	
	/**
	 * Livre contenant toutes les informations
	 */
	private Book book;

	/**
	 * Initialisation du prélude
	 * @param book Le livre contenant toutes les informations
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
	 * @return Le pane du texte
	 */
	private Node getPreludeTextPane() {
		GridPane preludeRoot = new GridPane();
		preludeRoot.setHgap(UiConsts.DEFAULT_MARGIN);
		preludeRoot.setVgap(UiConsts.DEFAULT_MARGIN);
		preludeRoot.setPadding(UiConsts.DEFAULT_INSET_DIALOG_MAIN_UI);
		
		texte = new TextArea();
		texte.setWrapText(true);
		preludeRoot.add(new Label("Texte :"), 0, 0);
		preludeRoot.add(texte, 0, 1, 2, 1);
		
		return preludeRoot;
	}
	
	/**
	 * Affichage pour l'ajout des items et du texte
	 * @return Le pane de la création des items et du texte
	 */
	private Node getCharacterCreationTab() {
		VBox characterCreationPane = new VBox();
		characterCreationPane.setSpacing(UiConsts.DEFAULT_MARGIN);
		characterCreationPane.setPadding(UiConsts.DEFAULT_INSET_DIALOG_MAIN_UI);
		
		accordion = new Accordion();
		
		Button addButton = new Button("Ajouter");
		addButton.setOnAction((ActionEvent e) -> {
			createTitledPane();
		});
		
		characterCreationPane.getChildren().addAll(accordion, addButton);
		
		scrollPane = new ScrollPane(characterCreationPane);
		scrollPane.setFitToWidth(true);
		
		return scrollPane;
	}
	
	/**
	 * Appel de la méthode pour créer un Accordion
	 */
	private void createTitledPane() {
		createTitledPane(null);
	}
	
	/**
	 * Création de l'intérieur d'un Accordion
	 * @param characterCreation Skill(s) et/ou item(s) et/ou texte à l'intérieur de Accordion
	 */
	private void createTitledPane(AbstractCharacterCreation characterCreation) {
		TitledPane titledPane = new TitledPane();
		Button remove = new Button("Supprimer cette partie");
		
		HBox removeBox = new HBox();
		removeBox.getChildren().add(remove);
		removeBox.setAlignment(Pos.CENTER_RIGHT);
		removeBox.setPadding(new Insets(0, UiConsts.DEFAULT_MARGIN_DIALOG, 0, 0));
		
		CharacterCreationComponent characterCreationPane = new CharacterCreationComponent(book, characterCreation);
		
		VBox titledPaneBox = new VBox();
		titledPaneBox.setSpacing(UiConsts.DEFAULT_MARGIN);
		titledPaneBox.getChildren().addAll(characterCreationPane, removeBox);
		
		remove.setOnAction((ActionEvent e) -> {
			accordion.getPanes().remove(titledPane);
			this.characterCreationComponent.remove(characterCreationPane);
		});
		
		titledPane.setContent(titledPaneBox);
		accordion.getPanes().add(titledPane);
		this.characterCreationComponent.add(characterCreationPane);
	}
	
	/**
	 * Création du pane pour la création du personnage principal
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
	 * Donne la liste des skills/items/textes du personnage principal
	 * @return skills/items/textes du personnage principal
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
