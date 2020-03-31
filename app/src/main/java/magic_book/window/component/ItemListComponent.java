package magic_book.window.component;

import java.util.List;
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

	private ComboBox<BookItem> itemComboBox;
	private ListView<BookItemLink> selectedItemsListView;
	
	private Button updateItemSelected;
	private TextField amountTextField;
	
	private Book book;
	
	public ItemListComponent(Book book) {
		this.book = book;
		
		this.setSpacing(UiConsts.DEFAULT_MARGIN);
		
		itemComboBox = new ComboBox<>();
		itemComboBox.getItems().addAll(book.getItems().values());
		
		Button addItemSelected = new Button("Ajouter");
		addItemSelected.setOnAction((ActionEvent e) -> {
			if(itemComboBox.getValue() != null)
				addItemLink(itemComboBox.getValue().getId());
		});
		
		HBox itemSelectionBox = new HBox();
		itemSelectionBox.setSpacing(UiConsts.DEFAULT_MARGIN);
		itemSelectionBox.getChildren().addAll(itemComboBox, addItemSelected);
	
		selectedItemsListView = new ListView<>();
		selectedItemsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);		
		selectedItemsListView.getSelectionModel().selectedIndexProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
			disableItemLinkEdition();
			
			if(selectedItemsListView.getSelectionModel().getSelectedItem() != null) {
				amountTextField.setDisable(false);
				updateItemSelected.setDisable(false);
				amountTextField.setText(""+selectedItemsListView.getSelectionModel().getSelectedItem().getAmount());
			}
		});
		
		selectedItemsListView.setContextMenu(createMenuContextItemLink());
		
		this.getChildren().addAll(itemSelectionBox, selectedItemsListView, createItemLinkPane());
	}
	
	public ContextMenu createMenuContextItemLink() {
		ContextMenu contextMenuItemLink = new ContextMenu();
		
		MenuItem menuItemLinkDelete = new MenuItem("Supprimer l'item");
		menuItemLinkDelete.setOnAction((ActionEvent event) -> {
			BookItemLink itemLink = selectedItemsListView.getSelectionModel().getSelectedItem();
			if(itemLink != null) {
				selectedItemsListView.getItems().remove(itemLink);
				selectedItemsListView.refresh();
				itemComboBox.getItems().add(book.getItems().get(itemLink.getId()));			
				disableItemLinkEdition();
			}
		});
		
		contextMenuItemLink.getItems().add(menuItemLinkDelete);
		
		return contextMenuItemLink;
	}
	
	public GridPane createItemLinkPane() {
		amountTextField = new TextField();
		
		updateItemSelected = new Button("Modifier");
		updateItemSelected.setOnAction((ActionEvent e) -> {
			BookItemLink itemLink = selectedItemsListView.getSelectionModel().getSelectedItem();
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
		
		GridPane selectedItemLinkPane = new GridPane();
		selectedItemLinkPane.add(new Label("Montant : "), 0, 0);
		selectedItemLinkPane.add(amountTextField, 1, 0);
		selectedItemLinkPane.add(updateItemSelected, 0, 1);
		
		return selectedItemLinkPane;
	}
	
	public void disableItemLinkEdition() {
		amountTextField.setDisable(true);
		updateItemSelected.setDisable(true);
		amountTextField.setText("");
	}
	
	public void addItemLink(String id) {
		BookItemLink bookItemLink = new BookItemLink();
		bookItemLink.setId(id);
		
		addItemLink(bookItemLink);
	}
	
	public void addItemLink(BookItemLink bookItemLink) {
		selectedItemsListView.getItems().add(bookItemLink);

		itemComboBox.getItems().remove(book.getItems().get(bookItemLink.getId()));
	}
	
	public List<BookItemLink> getBookItemLinks() {
		return selectedItemsListView.getItems();
	}
	
	public void setBookItemLinks(List<BookItemLink> itemLinks) {
		for(BookItemLink itemLink : itemLinks) {
			addItemLink(new BookItemLink(itemLink));
		}
	}
	
}
