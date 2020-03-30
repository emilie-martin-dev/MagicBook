package magic_book.window.dialog;

import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
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
import magic_book.window.component.CharacterComponent;

public class PreludeDialog extends AbstractDialog {
	
	private List<AbstractCharacterCreation> characterCreations;
	private BookCharacter mainCharacter;
	private String textePrelude;
	
	private TextArea texte;
	private Accordion accordion;
	private ScrollPane scrollPane;

	public PreludeDialog() {
		super("Creation du Prelude", true);

		this.showAndWait();
	}

	public PreludeDialog(String textePrelude) {
		super("Edition du Prelude", true);

		texte.setText(textePrelude);

		this.showAndWait();
	}

	@Override
	protected Node getMainUI() {
		TabPane tabPane = new TabPane();
		
		Tab tab1 = new Tab("Prélude");
		tab1.setClosable(false);
		Tab tab2 = new Tab("Création du personnage");
		tab2.setClosable(false);
		Tab tab3 = new Tab("Personnage de base");
		tab3.setClosable(false);

        tabPane.getTabs().add(tab1);
        tabPane.getTabs().add(tab2);
        tabPane.getTabs().add(tab3);
		
		GridPane root = new GridPane();
		root.setHgap(5);
		root.setVgap(5);

		Label textLabel = new Label("Texte :");
		texte = new TextArea();
		texte.setWrapText(true);
		root.add(textLabel, 0, 0);
		root.add(texte, 0, 1, 2, 1);
		
		tab1.setContent(root);
		
		Insets insets = new Insets(25, 25, 0, 25);
		root.setPadding(insets);
		
		accordion = new Accordion();
		BorderPane root2 = new BorderPane();
		root2.setCenter(accordion);
		Button addButton = new Button("Ajouter");
		addButton.setOnAction((ActionEvent e) -> {
			accordion.getPanes().add(createTitledPane());
		});
		root2.setBottom(addButton);
		
		scrollPane = new ScrollPane(root2);
		scrollPane.setFitToWidth(true);
		tab2.setContent(scrollPane);
		
		CharacterComponent characterComponent = new CharacterComponent();
		BorderPane root3 = new BorderPane();
		root3.setCenter(characterComponent);
		tab3.setContent(root3);
		
		return tabPane;
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

	@Override
	protected EventHandler<ActionEvent> getValidButtonEventHandler() {
		return (ActionEvent e) -> {
			this.textePrelude = (String) texte.getText();

			close();
		};
	}

	public String getTextePrelude() {
		return textePrelude;
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
