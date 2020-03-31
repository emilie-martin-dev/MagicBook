package magic_book.window.component;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import magic_book.core.Book;
import magic_book.core.game.character_creation.AbstractCharacterCreation;
import magic_book.core.game.character_creation.CharacterCreationItem;
import magic_book.core.game.character_creation.CharacterCreationText;
import magic_book.window.UiConsts;

public class CharacterCreationComponent extends GridPane {
		
		private static final String TYPE_TEXT = "Texte";
		//private static final String TYPE_SKILL = "Skills";
		private static final String TYPE_ITEM = "Item";
		
		private ComboBox<String> type;
		private ItemListComponent itemListComponent;
		private TextArea texte;
		
		public CharacterCreationComponent(Book book, AbstractCharacterCreation abstractCharacterCreation) {
			this.setHgap(UiConsts.DEFAULT_MARGIN);
			this.setVgap(UiConsts.DEFAULT_MARGIN);
			this.setPadding(new Insets(25, 25, 0, 25));

			Label textLabel = new Label("Texte :");
			texte = new TextArea();
			texte.setWrapText(true);
			
			type = new ComboBox<>();
			type.getItems().add(TYPE_TEXT);
			if(!book.getItems().isEmpty())
				type.getItems().add(TYPE_ITEM);
			
			type.setValue(TYPE_TEXT);
			
			this.add(textLabel, 0, 0);
			this.add(texte, 0, 1, 2, 1);
			this.add(new Label("Type"), 0, 2);
			this.add(type, 1, 2);
			itemListComponent = new ItemListComponent(book);
			
			type.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> ov, String t, String t1) -> {
				this.getChildren().remove(itemListComponent);
				if(type.getValue().equals(TYPE_ITEM)) {
					addListComponent();
				}
			});
			
			if(abstractCharacterCreation != null)
				setCharacterCreation(abstractCharacterCreation);
		}
		
		private void addListComponent() {
			this.add(itemListComponent, 0, 3, 2, 1);
		}
		
		public AbstractCharacterCreation getCharacterCreation() {
			AbstractCharacterCreation characterCreation = null;
			
			if(type.getValue() == TYPE_ITEM) {
				CharacterCreationItem characterCreationItem = new CharacterCreationItem();
				characterCreationItem.setItemLinks(itemListComponent.getBookItemLinks());
				
				characterCreation = characterCreationItem;
			} else if(type.getValue() == TYPE_TEXT) {
				characterCreation = new CharacterCreationText();
			}
			
			characterCreation.setText(texte.getText());
			
			return characterCreation;
		}
		
		public void setCharacterCreation(AbstractCharacterCreation characterCreation) {
			if(characterCreation instanceof CharacterCreationItem) {
				CharacterCreationItem characterCreationItem = (CharacterCreationItem) characterCreation;
				itemListComponent.setBookItemLinks(characterCreationItem.getItemLinks());
				type.setValue(TYPE_ITEM);
			}
			
			texte.setText(characterCreation.getText());
		}
		
	}