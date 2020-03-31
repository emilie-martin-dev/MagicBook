package magic_book.window.dialog;

import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import magic_book.core.game.BookCharacter;
import magic_book.core.game.character_creation.AbstractCharacterCreation;
import magic_book.window.UiConsts;
import magic_book.window.component.CharacterComponent;

public class PreludeDialog extends AbstractDialog {
	
	private List<AbstractCharacterCreation> characterCreations;
	private BookCharacter mainCharacter;
	private String textePrelude;
	
	private TextArea texte;
	private Accordion accordion;
	private ScrollPane scrollPane;
	private CharacterComponent characterComponent;

	public PreludeDialog(String textePrelude, BookCharacter mainCharacter) {
		super("Edition du Prelude", true);

		texte.setText(textePrelude);

		this.mainCharacter = mainCharacter;
		characterComponent.setCharacter(mainCharacter);
		
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
	
	private Node getCharacterCreationTab() {
		BorderPane characterCreationPane = new BorderPane();
		characterCreationPane.setPadding(UiConsts.DEFAULT_INSET_DIALOG_MAIN_UI);
		
		accordion = new Accordion();
		characterCreationPane.setCenter(accordion);
		
		Button addButton = new Button("Ajouter");
		addButton.setOnAction((ActionEvent e) -> {
			accordion.getPanes().add(createTitledPane());
		});
		
		characterCreationPane.setBottom(addButton);
		
		scrollPane = new ScrollPane(characterCreationPane);
		scrollPane.setFitToWidth(true);
		
		return scrollPane;
	}
	
	private TitledPane createTitledPane() {
		TitledPane titledPane = new TitledPane();
		Button remove = new Button("Supprimer");
		remove.setOnAction((ActionEvent e) -> {
			accordion.getPanes().remove(titledPane);
		});
		
		VBox box = new VBox();
		CharacterCreationPane characterCreationPane = new CharacterCreationPane();
		box.getChildren().addAll(characterCreationPane, remove);
		titledPane.setContent(box);
		
		return titledPane;
	}
	
	private Node getMainCharacterPane() {
		characterComponent = new CharacterComponent(null);
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

			close();
		};
	}

	public String getTextePrelude() {
		return textePrelude;
	}

	public List<AbstractCharacterCreation> getCharacterCreations() {
		return characterCreations;
	}

	public void setCharacterCreations(List<AbstractCharacterCreation> characterCreations) {
		this.characterCreations = characterCreations;
	}

	public BookCharacter getMainCharacter() {
		return mainCharacter;
	}

	public void setMainCharacter(BookCharacter mainCharacter) {
		this.mainCharacter = mainCharacter;
	}

	private class CharacterCreationPane extends GridPane {

		private AbstractCharacterCreation characterCreation;
		
		private static final String TYPE_TEXT = "Texte";
		//private static final String TYPE_SKILL = "Skills";
		private static final String TYPE_ITEM = "Item";
		
		private ComboBox<String> type;
		
		public CharacterCreationPane() {
			createUi();
		}

		private void createUi() {
			this.setHgap(5);
			this.setVgap(5);
			this.setPadding(new Insets(25, 25, 0, 25));

			Label textLabel = new Label("Texte :");
			TextArea texte = new TextArea();
			texte.setWrapText(true);
			
			type = new ComboBox<>();
			type.getItems().add(TYPE_TEXT); 
			type.getItems().add(TYPE_ITEM);
			type.setValue(TYPE_TEXT);
			
			this.add(textLabel, 0, 0);
			this.add(texte, 0, 1, 2, 1);
			this.add(new Label("Type"), 0, 2);
			this.add(type, 1, 2);
		}
		
		public AbstractCharacterCreation getCharacterCreation() {
			return characterCreation;
		}

		public void setCharacterCreation(AbstractCharacterCreation characterCreation) {
			this.characterCreation = characterCreation;
		}
	}
}
