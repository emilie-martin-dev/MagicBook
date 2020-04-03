package magic_book.window.pane;

import java.io.InputStream;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import magic_book.core.Book;
import magic_book.core.game.BookCharacter;
import magic_book.core.item.BookItem;
import magic_book.window.Mode;
import magic_book.window.UiConsts;
import magic_book.window.dialog.CharacterDialog;
import magic_book.window.dialog.ItemDialog;

/**
 * Création du coté gauche de la fenêtre Windows (Mode, Personnages, Items)
 */
public class LeftPane extends ScrollPane {
	
	/**
	 * Centre de la fenêtre
	 */
	private GraphPane graphPane;
	
	/**
	 * Bouton de mode
	 */
	private ToggleGroup toggleGroup;
	/**
	 * Vue sur les items
	 */
	private TreeView<BookItem> treeViewItem;
	/**
	 * Vue sur les personnages
	 */
	private TreeView<BookCharacter> treeViewPerso;
	
	/**
	 * Livre contenant toutes les informations
	 */
	private Book book;
	
	/**
	 * Initialisation des valeurs du Pane
	 * @param graphPane Centre de la fenêtre MainWindows
	 * @param book Livre contenant toutes les informations
	 */
	public LeftPane(GraphPane graphPane, Book book){
		this.graphPane = graphPane;
		this.book = book;
		
		this.setMaxWidth(UiConsts.LEFT_PANEL_SIZE);
		this.setMinWidth(UiConsts.LEFT_PANEL_SIZE);
		this.setPadding(UiConsts.DEFAULT_INSET);
		this.setFitToWidth(true);
				
		this.setContent(createLeftPanel());
	}
	
	/**
	 * Création de tout le panel : bouton de mode, vue sur les items, vue sur les personnages
	 * @return Tout le panel
	 */
	private Node createLeftPanel() {
		VBox leftContent = new VBox();
		
		//Création des boutons de mode
		ToggleButton selectToogle = createToggleButton("icons/select.png", Mode.SELECT);
		ToggleButton addNodeToggle = createToggleButton("icons/add_node.png", Mode.ADD_NODE);
		ToggleButton addNodeLinkToggle = createToggleButton("icons/add_link.png", Mode.ADD_NODE_LINK);
		ToggleButton suppNode = createToggleButton("icons/delete.png", Mode.DELETE);
		ToggleButton firstNode = createToggleButton("icons/first_node.png", Mode.FIRST_NODE);
		
		selectToogle.setSelected(true);
		graphPane.setMode(Mode.SELECT);

		FlowPane flow = new FlowPane();
		flow.setHgap(UiConsts.DEFAULT_MARGIN);
		flow.setVgap(UiConsts.DEFAULT_MARGIN);
		flow.getChildren().addAll(selectToogle, addNodeToggle, addNodeLinkToggle, suppNode, firstNode);
		leftContent.setSpacing(UiConsts.DEFAULT_MARGIN);
		leftContent.getChildren().add(flow);
		
		//Vue sur les items et personnages
		VBox itemPerso = gestionPerso();
		leftContent.getChildren().add(itemPerso);
		
		return leftContent;
	}
	
	/**
	 * Création de la vue sur les items et les personnages
	 * @return VBox contenant la vue
	 */
	private VBox gestionPerso(){

		//---Création des TreeItem avec les items/persos
		TreeItem<BookCharacter> rootPerso = new TreeItem<> (new BookCharacter("0", "Personnage", 0, 0, null, null, 0));
		rootPerso.setExpanded(true);
		treeViewPerso = new TreeView<> (rootPerso);
		
		TreeItem<BookItem> rootItem = new TreeItem<> (new BookItem("0","Items"));
		rootItem.setExpanded(true);
		
		treeViewItem = new TreeView<> (rootItem);

		//---Création des context menus pour ajouter/supprimer des personnages
		ContextMenu contextMenuPerso = new ContextMenu();
		MenuItem menuPersoAdd = new MenuItem("Ajouter un Personnage");
		MenuItem menuPersoUpdate = new MenuItem("Modifier un Personnage");
		MenuItem menuPersoDel = new MenuItem("Supprimer un Personnage");

		//Si un clique a été enregistré sur "Ajouter un personnage"
		menuPersoAdd.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				CharacterDialog characterDialog = new CharacterDialog(LeftPane.this.book);
				BookCharacter perso = characterDialog.getCharacter();
				if(perso != null) {
					addCharacter(perso);
				}
			}
		});

		//Si un clique a été enregistré sur "Modifier un personnage"
		menuPersoUpdate.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				TreeItem<BookCharacter> selectedItem = treeViewPerso.getSelectionModel().getSelectedItem();
				if(selectedItem != null) {
					BookCharacter character = selectedItem.getValue();
					String oldId = character.getId();
					CharacterDialog characterDialog = new CharacterDialog(character, LeftPane.this.book);
					
					if(characterDialog.getCharacter() == null)
						return;
					
					character = characterDialog.getCharacter();
					
					book.getCharacters().remove(oldId);	
					book.getCharacters().put(character.getId(), character);
					selectedItem.setValue(character);
					
					treeViewPerso.refresh();
				}
			}
		});
		
		//Si un clique a été enregistré sur "Supprimer un personnage"
		menuPersoDel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				TreeItem<BookCharacter> selectedItem = treeViewPerso.getSelectionModel().getSelectedItem();
				rootPerso.getChildren().remove(selectedItem);
				book.getCharacters().remove(selectedItem.getValue().getId());
			}
		});
		
		contextMenuPerso.getItems().addAll(menuPersoAdd, menuPersoUpdate, menuPersoDel);
		treeViewPerso.setContextMenu(contextMenuPerso);

		//---Création des context menus pour ajouter/supprimer des items
		ContextMenu contextMenuItem = new ContextMenu();
		MenuItem menuItemAdd = new MenuItem("Ajouter un Item");
		MenuItem menuItemUpdate = new MenuItem("Modifier un Item");
		MenuItem menuItemDel = new MenuItem("Supprimer un Item");

		//Si un clique a été enregistré sur "Ajouter un item"
		menuItemAdd.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ItemDialog itemDialog = new ItemDialog(LeftPane.this.book);
				BookItem item = itemDialog.getItem();
				if(item != null) {
					addItem(item);
				}
			}
		});
		
		//Si un clique a été enregistré sur "Modifier un item"
		menuItemUpdate.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				TreeItem<BookItem> selectedItem = treeViewItem.getSelectionModel().getSelectedItem();
				if(selectedItem != null) {
					BookItem item = selectedItem.getValue();
					String oldId = item.getId();
					ItemDialog newItemDialog = new ItemDialog(item, LeftPane.this.book);
					BookItem newItem = newItemDialog.getItem();
					
					if(newItem == null){
						return;
					}
					
					selectedItem.setValue(newItem);
					treeViewItem.refresh();
					
					book.getItems().remove(oldId);	
					book.getItems().put(newItem.getId(), newItem);
				}
			}
		});

		//Si un clique a été enregistré sur "Supprimer un item"
		menuItemDel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				TreeItem<BookItem> selectedItem = treeViewItem.getSelectionModel().getSelectedItem();
				rootItem.getChildren().remove(selectedItem);
				book.getItems().remove(selectedItem.getValue().getId());
			}
		});
		
		contextMenuItem.getItems().addAll(menuItemAdd, menuItemUpdate, menuItemDel);
		treeViewItem.setContextMenu(contextMenuItem);

		VBox vBox = new VBox(treeViewPerso, treeViewItem);
		vBox.setSpacing(UiConsts.DEFAULT_MARGIN);

		return vBox;
	}
	
	/**
	 * Si un livre est ouvert, toute la vue sur les items et personnages se met à jour
	 * @param book Nouveau livre contenant toutes les informations
	 */
	public void setBook(Book book) {
		treeViewPerso.getRoot().getChildren().clear();
		treeViewItem.getRoot().getChildren().clear();
		
		for(Map.Entry<String, BookCharacter> entry : book.getCharacters().entrySet()) {
			if(!entry.getKey().equals(Book.MAIN_CHARACTER_ID))
				addCharacter(entry.getValue());
		}
		
		
		for(Map.Entry<String, BookItem> entry : book.getItems().entrySet()) {
			addItem(entry.getValue());
		}
		
		this.book = book;
	}

	/**
	 * Si on ajoute un personnage, une vue s'ajoute sur celui-çi afin de l'afficher
	 * @param character Personnage à ajouter
	 */
	private void addCharacter(BookCharacter character){
		treeViewPerso.getRoot().getChildren().add(new TreeItem<> (character));
		book.getCharacters().put(character.getId(), character);
	}

	/**
	 * Si on ajoute un item, une vue s'ajoute sur celui-çi afin de l'afficher
	 * @param item Item à ajouter
	 */
	private void addItem(BookItem item){
		treeViewItem.getRoot().getChildren().add(new TreeItem<> (item));
		book.getItems().put(item.getId(), item);
	}

	/**
	 * Création d'un bouton de mode
	 * @param path Adresse de l'image
	 * @param mode Mode attribué à ce bouton
	 * @return Le bouton créer
	 */
	private ToggleButton createToggleButton(String path, Mode mode) {
		InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(path);
		ImageView imageView = new ImageView(new Image(stream));
		imageView.setFitHeight(40);		
		imageView.setFitWidth(40);
		ToggleButton toggleButton = new ToggleButton("", imageView);
		
		imageView.setPreserveRatio(true);
		toggleButton.setMinSize(UiConsts.CONTROL_BUTTON_SIZE, UiConsts.CONTROL_BUTTON_SIZE);
		toggleButton.setMaxSize(UiConsts.CONTROL_BUTTON_SIZE, UiConsts.CONTROL_BUTTON_SIZE);

		toggleButton.setOnAction((ActionEvent e) -> {
			if(toggleGroup.getSelectedToggle() == null) {
				toggleGroup.selectToggle(toggleButton);
				return;
			}
			
			graphPane.setMode(mode);
			graphPane.setSelectedNodeFx(null);
		});

		if (this.toggleGroup == null) {
			this.toggleGroup = new ToggleGroup();
		}

		this.toggleGroup.getToggles().add(toggleButton);

		return toggleButton;
	}
}
