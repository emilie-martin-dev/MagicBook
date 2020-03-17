package magic_book.window.component;

import java.util.Map;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import magic_book.core.Book;
import magic_book.core.game.BookCharacter;
import magic_book.core.item.BookItem;
import magic_book.window.Mode;
import magic_book.window.dialog.CharacterDialog;
import magic_book.window.dialog.ItemDialog;

public class LeftPane extends Pane{
	
	private GraphPane graphPane;
	
	private ToggleGroup toggleGroup;
	private TreeView<BookItem> treeViewItem;
	private TreeView<BookCharacter> treeViewPerso;
	
	public LeftPane(GraphPane graphPane){
		this.graphPane = graphPane;
		this.getChildren().add(createLeftPanel());
	}
	
	private Node createLeftPanel() {
		VBox leftContent = new VBox();
		
		ToggleButton selectToogle = createToggleButton("Selectionner", Mode.SELECT);
		ToggleButton addNodeToggle = createToggleButton("Ajouter noeud", Mode.ADD_NODE);
		ToggleButton addNodeLinkToggle = createToggleButton("Ajouter lien", Mode.ADD_NODE_LINK);
		ToggleButton suppNode = createToggleButton("Supprime noeud", Mode.DELETE);
		ToggleButton firstNode = createToggleButton("Sélectionner premier noeud", Mode.FIRST_NODE);
		
		selectToogle.setSelected(true);
		graphPane.setMode(Mode.SELECT);

		FlowPane flow = new FlowPane();
		flow.getChildren().addAll(selectToogle, addNodeToggle, addNodeLinkToggle, suppNode, firstNode);
		leftContent.setMaxWidth(250);
		leftContent.setPadding(new Insets(5, 5, 5, 5));
		leftContent.setSpacing(15);
		leftContent.getChildren().add(flow);
		
		VBox ItemsPersos = gestionPerso();
		leftContent.getChildren().add(ItemsPersos);
		
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
				CharacterDialog characterDialog = new CharacterDialog();
				BookCharacter perso = characterDialog.getCharacter();
				if(perso != null) {
					addCharacter(perso);
				}
			}
		});

		menuPersoUpdate.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				TreeItem<BookCharacter> selectedItem = treeViewPerso.getSelectionModel().getSelectedItem();
				if(selectedItem != null) {
					BookCharacter character = selectedItem.getValue();
					new CharacterDialog(character);
					
					treeViewPerso.refresh();
				}
			}
		});
		
		menuPersoDel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				TreeItem<BookCharacter> selectedItem = treeViewPerso.getSelectionModel().getSelectedItem();
				rootPerso.getChildren().remove(selectedItem);
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
				ItemDialog itemDialog = new ItemDialog();
				BookItem item = itemDialog.getItem();
				if(item != null) {
					addItem(item);
				}
			}
		});
		
		menuItemUpdate.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				TreeItem<BookItem> selectedItem = treeViewItem.getSelectionModel().getSelectedItem();
				if(selectedItem != null) {
					BookItem item = selectedItem.getValue();
					new ItemDialog(item);
					
					treeViewItem.refresh();
				}
			}
		});

		menuItemDel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				TreeItem<BookItem> selectedItem = treeViewItem.getSelectionModel().getSelectedItem();
				rootItem.getChildren().remove(selectedItem);
			}
		});
		
		contextMenuItem.getItems().addAll(menuItemAdd, menuItemUpdate, menuItemDel);
		treeViewItem.setContextMenu(contextMenuItem);

		VBox vBox = new VBox(treeViewPerso, treeViewItem);
		vBox.setSpacing(5);

		return vBox;
	}
	
	public void setBook(Book book) {
		treeViewPerso.getRoot().getChildren().clear();
		treeViewItem.getRoot().getChildren().clear();
		
		for(Map.Entry<String, BookCharacter> entry : book.getCharacters().entrySet()) {
			addCharacter(entry.getValue());
		}
		
		
		for(Map.Entry<String, BookItem> entry : book.getItems().entrySet()) {
			addItem(entry.getValue());
		}
	}

	private void addCharacter(BookCharacter character){
		treeViewPerso.getRoot().getChildren().add(new TreeItem<> (character));
	}

	private void addItem(BookItem item){
		treeViewItem.getRoot().getChildren().add(new TreeItem<> (item));
	}

	private ToggleButton createToggleButton(String text, Mode mode) {
		ToggleButton toggleButton = new ToggleButton(text);

		toggleButton.setOnAction((ActionEvent e) -> {
			graphPane.setMode(mode);
			graphPane.setSelectedNodeFx(null);
		});

		toggleButton.setPrefSize(100, 100);

		if (this.toggleGroup == null) {
			this.toggleGroup = new ToggleGroup();
		}

		this.toggleGroup.getToggles().add(toggleButton);

		return toggleButton;
	}
}
