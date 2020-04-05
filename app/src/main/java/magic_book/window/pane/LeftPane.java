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
import magic_book.observer.book.BookCharacterObserver;
import magic_book.observer.book.BookItemObserver;
import magic_book.window.Mode;
import magic_book.window.UiConsts;
import magic_book.window.dialog.CharacterDialog;
import magic_book.window.dialog.ItemDialog;

public class LeftPane extends ScrollPane implements BookItemObserver, BookCharacterObserver {
	
	private GraphPane graphPane;
	
	private ToggleGroup toggleGroup;
	private TreeView<BookItem> treeViewItem;
	private TreeView<BookCharacter> treeViewPerso;
	
	private Book book;
	
	public LeftPane(GraphPane graphPane, Book book){
		this.graphPane = graphPane;
		
		this.setMaxWidth(UiConsts.LEFT_PANEL_SIZE);
		this.setMinWidth(UiConsts.LEFT_PANEL_SIZE);
		this.setPadding(UiConsts.DEFAULT_INSET);
		this.setFitToWidth(true);
				
		this.setContent(createLeftPanel());
		setBook(book);
	}
	
	private Node createLeftPanel() {
		VBox leftContent = new VBox();
		
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
		
		VBox itemPerso = gestionPerso();
		leftContent.getChildren().add(itemPerso);
		
		return leftContent;
	}
	
	private VBox gestionPerso(){

		//Création des TreeItem avec les items/persos
		TreeItem<BookCharacter> rootPerso = new TreeItem<> (new BookCharacter("0", "Personnage", 0, 0, null, null, 0));
		rootPerso.setExpanded(true);
		treeViewPerso = new TreeView<> (rootPerso);
		
		TreeItem<BookItem> rootItem = new TreeItem<> (new BookItem("0","Items"));
		rootItem.setExpanded(true);
		
		treeViewItem = new TreeView<> (rootItem);

		//Création des context menus pour ajouter/supprimer des personnages
		ContextMenu contextMenuPerso = new ContextMenu();
		MenuItem menuPersoAdd = new MenuItem("Ajouter un Personnage");
		MenuItem menuPersoUpdate = new MenuItem("Modifier un Personnage");
		MenuItem menuPersoDel = new MenuItem("Supprimer un Personnage");

		menuPersoAdd.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				CharacterDialog characterDialog = new CharacterDialog(LeftPane.this.book);
				BookCharacter perso = characterDialog.getCharacter();
				
				if(perso != null) {
					book.addCharacter(perso);
				}
			}
		});

		menuPersoUpdate.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				TreeItem<BookCharacter> selectedItem = treeViewPerso.getSelectionModel().getSelectedItem();
				if(selectedItem != null) {
					BookCharacter oldCharacter = selectedItem.getValue();
					
					CharacterDialog characterDialog = new CharacterDialog(oldCharacter, LeftPane.this.book);
					BookCharacter newCharacter = characterDialog.getCharacter();
					
					if(newCharacter == null)
						return;
					
					book.updateCharacter(oldCharacter, newCharacter);
				}
			}
		});
		
		menuPersoDel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				TreeItem<BookCharacter> selectedItem = treeViewPerso.getSelectionModel().getSelectedItem();
				book.removeCharacter(selectedItem.getValue());
			}
		});
		
		contextMenuPerso.getItems().addAll(menuPersoAdd, menuPersoUpdate, menuPersoDel);
		treeViewPerso.setContextMenu(contextMenuPerso);

		//Création des context menus pour ajouter/supprimer des items
		ContextMenu contextMenuItem = new ContextMenu();
		MenuItem menuItemAdd = new MenuItem("Ajouter un Item");
		MenuItem menuItemUpdate = new MenuItem("Modifier un Item");
		MenuItem menuItemDel = new MenuItem("Supprimer un Item");

		menuItemAdd.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ItemDialog itemDialog = new ItemDialog(LeftPane.this.book);
				BookItem item = itemDialog.getItem();
				if(item != null) {
					book.addItem(item);
				}
			}
		});
		
		menuItemUpdate.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				TreeItem<BookItem> selectedItem = treeViewItem.getSelectionModel().getSelectedItem();
				if(selectedItem != null) {
					BookItem oldItem = selectedItem.getValue();
					
					ItemDialog newItemDialog = new ItemDialog(oldItem, LeftPane.this.book);
					BookItem newItem = newItemDialog.getItem();
					
					if(newItem == null){
						return;
					}
					
					book.updateItem(oldItem, newItem);
				}
			}
		});

		menuItemDel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				TreeItem<BookItem> selectedItem = treeViewItem.getSelectionModel().getSelectedItem();
				book.removeItem(selectedItem.getValue());
			}
		});
		
		contextMenuItem.getItems().addAll(menuItemAdd, menuItemUpdate, menuItemDel);
		treeViewItem.setContextMenu(contextMenuItem);

		VBox vBox = new VBox(treeViewPerso, treeViewItem);
		vBox.setSpacing(UiConsts.DEFAULT_MARGIN);

		return vBox;
	}
	
	public void setBook(Book book) {
		if(this.book != null) {
			this.book.removeCharacterObserver(this);
			this.book.removeItemObserver(this);
		}
			
		this.book = book;
		
		this.book.addCharacterObserver(this);
		this.book.addItemObserver(this);
		
		treeViewPerso.getRoot().getChildren().clear();
		treeViewItem.getRoot().getChildren().clear();
		
		for(Map.Entry<String, BookCharacter> entry : book.getCharacters().entrySet()) {
			if(!entry.getKey().equals(Book.MAIN_CHARACTER_ID))
				characterAdded(entry.getValue());
		}
		
		
		for(Map.Entry<String, BookItem> entry : book.getItems().entrySet()) {
			itemAdded(entry.getValue());
		}
	}

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

	@Override
	public void itemAdded(BookItem item) {
		treeViewItem.getRoot().getChildren().add(new TreeItem<> (item));	
	}

	@Override
	public void itemEdited(BookItem oldItem, BookItem newItem) {
		for(TreeItem<BookItem> treeItem : treeViewItem.getRoot().getChildren()) {
			if(treeItem.getValue() == oldItem) {
				treeItem.setValue(newItem);
				break;
			}
		}
		
		treeViewItem.refresh();
	}

	@Override
	public void itemDeleted(BookItem item) {
		for(TreeItem<BookItem> treeItem : treeViewItem.getRoot().getChildren()) {
			if(treeItem.getValue() == item) {
				treeViewItem.getRoot().getChildren().remove(treeItem);
				break;
			}
		}
	}

	@Override
	public void characterAdded(BookCharacter character) {
		treeViewPerso.getRoot().getChildren().add(new TreeItem<> (character));
	}

	@Override
	public void characterEdited(BookCharacter oldCharacter, BookCharacter newCharacter) {
		for(TreeItem<BookCharacter> treeItem : treeViewPerso.getRoot().getChildren()) {
			if(treeItem.getValue() == oldCharacter) {
				treeItem.setValue(newCharacter);
				break;
			}
		}
		
		treeViewPerso.refresh();
	}

	@Override
	public void characterDeleted(BookCharacter character) {
		for(TreeItem<BookCharacter> treeItem : treeViewPerso.getRoot().getChildren()) {
			if(treeItem.getValue() == character) {
				treeViewPerso.getRoot().getChildren().remove(treeItem);
				break;
			}
		}
	}
}
