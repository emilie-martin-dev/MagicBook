package magic_book.window.component;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import magic_book.core.Book;
import magic_book.core.item.BookItem;
import magic_book.core.item.BookItemLink;
import magic_book.window.UiConsts;

public class ItemListComponent extends VBox {

	private ComboBox<BookItem> items;
	
	public ItemListComponent(Book book) {
		this.setSpacing(UiConsts.DEFAULT_MARGIN);
		
		HBox itemBox = new HBox();
		itemBox.setSpacing(UiConsts.DEFAULT_MARGIN);
		
		items = new ComboBox<>();
		items.getItems().addAll(book.getItems().values());
		
		Button addItem = new Button("Ajouter");
		Button updateItem = new Button("Modifier");

		itemBox.getChildren().addAll(items, addItem);
	
		ListView<BookItemLink> selectedItem = new ListView<>();
		selectedItem.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
		GridPane selectedItemPane = new GridPane();
		TextField amountTextField = new TextField();
		selectedItemPane.add(new Label("Montant : "), 0, 0);
		selectedItemPane.add(amountTextField, 1, 0);
		selectedItemPane.add(updateItem, 0, 1);
		
		amountTextField.setDisable(true);
		updateItem.setDisable(true);
		
		this.getChildren().addAll(itemBox, selectedItem, selectedItemPane);
		
		addItem.setOnAction((ActionEvent e) -> {
			BookItemLink bookItemLink = new BookItemLink();
			bookItemLink.setId(items.getValue().getId());
			selectedItem.getItems().add(bookItemLink);
			
			items.getItems().remove(items.getValue());
		});
		
		selectedItem.getSelectionModel().selectedIndexProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
			amountTextField.setDisable(true);
			updateItem.setDisable(true);
			amountTextField.setText("");
			
			if(selectedItem.getSelectionModel().getSelectedItem() != null) {
				amountTextField.setDisable(false);
				updateItem.setDisable(false);
				amountTextField.setText(""+selectedItem.getSelectionModel().getSelectedItem().getAmount());
			}
		});
		
		ContextMenu contextMenuItemLink = new ContextMenu();
		MenuItem menuItemLinkDelete = new MenuItem("Supprimer l'item");
		menuItemLinkDelete.setOnAction((ActionEvent event) -> {
			BookItemLink itemLink = selectedItem.getSelectionModel().getSelectedItem();
			if(itemLink != null) {
				selectedItem.getItems().remove(itemLink);
				selectedItem.refresh();
				items.getItems().add(book.getItems().get(itemLink.getId()));
			}
		});
		contextMenuItemLink.getItems().add(menuItemLinkDelete);
		
		selectedItem.setContextMenu(contextMenuItemLink);
		
		updateItem.setOnAction((ActionEvent e) -> {
			BookItemLink itemLink = selectedItem.getSelectionModel().getSelectedItem();
			if(itemLink != null) {
				int amount = 1;
				
				try {
					amount = Integer.valueOf(amountTextField.getText());
				} catch(NumberFormatException ex) {
					return;
				}
				
				itemLink.setAmount(amount);
			}
		});
	}
	
}
