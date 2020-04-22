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

/**
 * Permet de sélectionner des items parmis ceux disponibles
 */
public class ItemListComponent extends VBox {

	/**
	 * Contient la liste d'item disponible
	 */
	private ComboBox<BookItem> itemComboBox;
	/**
	 * Contient la liste d'item sélectionné
	 */
	private ListView<BookItemLink> selectedItemsListView;
	
	/**
	 * Bouton de mise à jour d'item
	 */
	private Button updateItemSelected;
	/**
	 * Prix d'achat de l'item
	 */
	private TextField priceTextField;
	/**
	 * Prix de vente de l'item
	 */
	private TextField sellingPriceTextField;
	/**
	 * Nombre d'item disponible (par item)
	 */
	private TextField amountTextField;
	
	/**
	 * Livre contenant toutes les informations
	 */
	private Book book;
	
	/**
	 * Constructeur
	 * @param book Livre contenant toutes les informations
	 * @param isShop True si il faut afficher les champs pour le shop
	 */
	public ItemListComponent(Book book, boolean isShop) {
		this.book = book;
		this.setSpacing(UiConsts.DEFAULT_MARGIN);
		
		createAddItemLinkUi();
		createSelectedItemLinkUi(isShop);
		createItemLinkEditPane(isShop);
		
		disableItemLinkEdition();
	}
	
	/**
	 * Créé l'UI pour le bouton d'ajout et la liste déroulante
	 */
	private void createAddItemLinkUi(){
		itemComboBox = new ComboBox<>();
		itemComboBox.getItems().addAll(book.getItems().values());
		
		//Bouton "Ajouter" pour ajouter les items
		Button addItemSelected = new Button("Ajouter");
		addItemSelected.setOnAction((ActionEvent e) -> {
			//Ajoute l'item si un item est sélectionné dans la ComboBox
			if(itemComboBox.getValue() != null)
				addItemLink(itemComboBox.getValue().getId());
		});
		
		HBox itemSelectionBox = new HBox();
		itemSelectionBox.setSpacing(UiConsts.DEFAULT_MARGIN);
		itemSelectionBox.getChildren().addAll(itemComboBox, addItemSelected);
		
		this.getChildren().add(itemSelectionBox);
	}
	
	/**
	 * Créé la liste contenant les items que l'on a rendu disponible 
	 * @param isShop Si l'on doit changer les champs du shop lorsque l'on sélectionne un item
	 */
	private void createSelectedItemLinkUi(boolean isShop){
		//Permet d'afficher les items ajoutés
		selectedItemsListView = new ListView<>();
		selectedItemsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);		
		selectedItemsListView.getSelectionModel().selectedIndexProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
			disableItemLinkEdition();
			
			if(selectedItemsListView.getSelectionModel().getSelectedItem() != null) {
				amountTextField.setDisable(false);
				updateItemSelected.setDisable(false);
				sellingPriceTextField.setDisable(false);
				priceTextField.setDisable(false);
				amountTextField.setText(""+selectedItemsListView.getSelectionModel().getSelectedItem().getAmount());
				if(isShop){
					priceTextField.setText(""+selectedItemsListView.getSelectionModel().getSelectedItem().getPrice());
					sellingPriceTextField.setText(""+selectedItemsListView.getSelectionModel().getSelectedItem().getSellingPrice());
				}
			}
		});
		
		selectedItemsListView.setContextMenu(createSelectedItemLinkMenuContext());
		
		this.getChildren().add(selectedItemsListView);
	}
	
	/**
	 * Context Menu pour la liste des items sélectionnés
	 * @return Menu Context pour la liste des items sélectionnés
	 */
	private ContextMenu createSelectedItemLinkMenuContext() {
		ContextMenu contextMenuItemLink = new ContextMenu();
		
		MenuItem menuItemLinkDelete = new MenuItem("Supprimer l'item");
		//Si un clique sur louton "Supprimer l'item"
		menuItemLinkDelete.setOnAction((ActionEvent event) -> {
			BookItemLink itemLink = selectedItemsListView.getSelectionModel().getSelectedItem();
			//Supprime l'item selectionné
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
	
	/**
	 * Pane pour l'edition d'un item de la liste
	 * @param isShop True si c'est un shop, on affiche donc les champs correspondants 
	 */
	private void createItemLinkEditPane(boolean isShop) {
		priceTextField = new TextField();
		sellingPriceTextField = new TextField();
		amountTextField = new TextField();
		
		updateItemSelected = new Button("Modifier");
		updateItemSelected.setOnAction((ActionEvent e) -> {
			BookItemLink itemLink = selectedItemsListView.getSelectionModel().getSelectedItem();
			if(itemLink != null) {
				int amount = 1;
				int price = 0;
				int sellingPrice = 0;
				try {
					amount = Integer.valueOf(amountTextField.getText());
					
					if(isShop){
						price = Integer.valueOf(priceTextField.getText());
						sellingPrice = Integer.valueOf(sellingPriceTextField.getText());
						
						itemLink.setPrice(price);
						itemLink.setSellingPrice(sellingPrice);
					}
					
					itemLink.setAmount(amount);
				} catch(NumberFormatException ex) {
					return;
				}
			}
		});
		
		GridPane selectedItemLinkPane = new GridPane();
		selectedItemLinkPane.setVgap(UiConsts.DEFAULT_MARGIN);
		selectedItemLinkPane.setHgap(UiConsts.DEFAULT_MARGIN);
		selectedItemLinkPane.add(new Label("Montant : "), 0, 0);
		selectedItemLinkPane.add(amountTextField, 1, 0);
		if(isShop){
			selectedItemLinkPane.add(new Label("Prix d'achat : "), 0, 1);
			selectedItemLinkPane.add(priceTextField, 1, 1);
			selectedItemLinkPane.add(new Label("Prix de vente : "), 0, 2);
			selectedItemLinkPane.add(sellingPriceTextField, 1, 2);
			selectedItemLinkPane.add(updateItemSelected, 0, 3);
		} else {
			selectedItemLinkPane.add(updateItemSelected, 0, 1);
		}
		
		this.getChildren().add(selectedItemLinkPane);
	}
	
	/**
	 * Désactive les champs pour l'édition de l'item
	 */
	private void disableItemLinkEdition() {
		updateItemSelected.setDisable(true);
		amountTextField.setDisable(true);
		sellingPriceTextField.setDisable(true);
		priceTextField.setDisable(true);
		
		amountTextField.setText("");
		sellingPriceTextField.setText("");
		priceTextField.setText("");
	}
	
	/**
	 * Ajoute un item selon son id dans le noeud
	 * @param id L'item ajouté
	 */
	private void addItemLink(String id) {
		BookItemLink bookItemLink = new BookItemLink();
		bookItemLink.setId(id);
		
		addItemLink(bookItemLink);
	}
	
	/**
	 * Ajoute un item préexistant dans la liste d'item sélectionné
	 * @param bookItemLink 
	 */
	private void addItemLink(BookItemLink bookItemLink) {
		selectedItemsListView.getItems().add(bookItemLink);

		itemComboBox.getItems().remove(book.getItems().get(bookItemLink.getId()));
	}
	
	/**
	 * Change les items disponibles dans la liste
	 * @param itemsAvailable Les items disponibles
	 */
	public void setAvailableItem(List<BookItem> itemsAvailable) {
		itemComboBox.getItems().clear();
		itemComboBox.getItems().addAll(itemsAvailable);
	}
	
	/**
	 * Liste d'item(s) disponible dans le noeud
	 * @return Liste d'item(s)
	 */
	public List<BookItemLink> getBookItemLinks() {
		return selectedItemsListView.getItems();
	}
	
	/**
	 * Modifie la liste d'item disponible dans le noeud
	 * @param itemLinks Nouvelle liste d'item(s)
	 */
	public void setBookItemLinks(List<BookItemLink> itemLinks) {
		for(BookItemLink itemLink : itemLinks) {
			addItemLink(new BookItemLink(itemLink));
		}
	}
	
}
