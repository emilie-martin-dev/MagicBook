package magic_book.window;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import magic_book.core.node.BookNodeLink;
import magic_book.observer.NodeFxObserver;
import magic_book.window.dialog.NodeDialog;
import magic_book.window.dialog.NodeLinkDialog;
import magic_book.window.gui.NodeFx;

public class MainWindow extends Stage implements NodeFxObserver {

	private Mode mode;
	private ToggleGroup toggleGroup;
	
	private List<NodeFx> listeNoeud;
	
	private NodeFx firstNodeFxSelected;
	
	private Pane mainContent;

	public MainWindow() {
		BorderPane root = new BorderPane();

		listeNoeud = new ArrayList<>();
		
		mainContent = new Pane();
		mainContent.setCursor(Cursor.CLOSED_HAND);
		mainContent.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
			if (mode == Mode.ADD_NODE) {
				createNode((int) event.getX(), (int) event.getY());
			}
		});
		
		root.setTop(createMenuBar());
		root.setLeft(createLeftPanel());
		root.setCenter(mainContent);

		Scene scene = new Scene(root, 1000, 800);

		this.setTitle("Magic Book");
		this.setScene(scene);
		this.show();
	}

	private MenuBar createMenuBar() {
		MenuBar menuBar = new MenuBar();

		// --- Menu fichier
		Menu menuFile = new Menu("Fichier");
		MenuItem menuFileNew = new MenuItem("Nouveau");
		MenuItem menuFileOpen = new MenuItem("Ouvrir");
		MenuItem menuFileSave = new MenuItem("Enregistrer");
		MenuItem menuFileSaveAs = new MenuItem("Enregistrer sous");

		menuFile.getItems().addAll(menuFileNew, menuFileOpen, menuFileSave, menuFileSaveAs);

		// --- Menu livre
		Menu menuBook = new Menu("Livre");
		MenuItem menuBookDifficulty = new MenuItem("Estimer la difficulté");
		MenuItem menuBookGenerate = new MenuItem("Générer le livre");

		menuBook.getItems().addAll(menuBookDifficulty, menuBookGenerate);

		// --- Menu affichage
		Menu menuShow = new Menu("Affichage");
		CheckMenuItem menuShowItemsCharacters = new CheckMenuItem("Items et personnage");
		CheckMenuItem menuShowStats = new CheckMenuItem("Stats");
		menuShowItemsCharacters.setSelected(true);
		menuShowStats.setSelected(true);

		menuShow.getItems().addAll(menuShowItemsCharacters, menuShowStats);

		menuBar.getMenus().addAll(menuFile, menuBook, menuShow);

		return menuBar;
	}

	private Node createLeftPanel() {
		ToggleButton selectToogle = createToggleButton("Selectionner", Mode.SELECT);
		ToggleButton addNodeToggle = createToggleButton("Ajouter noeud", Mode.ADD_NODE);
		ToggleButton addNodeLinkToggle = createToggleButton("Ajouter lien", Mode.ADD_NODE_LINK);
		ToggleButton suppNode = createToggleButton("Supprime Noeud", Mode.SUPPRIMER);
		
		selectToogle.setSelected(true);
		this.mode = Mode.SELECT;

		FlowPane flow = new FlowPane();
		flow.getChildren().addAll(selectToogle, addNodeToggle, addNodeLinkToggle, suppNode);
		flow.setPadding(new Insets(5, 5, 5, 5));

		return flow;
	}

	private ToggleButton createToggleButton(String text, Mode mode) {
		ToggleButton toggleButton = new ToggleButton(text);

		toggleButton.setOnAction((ActionEvent e) -> {
			MainWindow.this.mode = mode;
		});

		toggleButton.setPrefSize(100, 100);

		if (this.toggleGroup == null) {
			this.toggleGroup = new ToggleGroup();
		}

		this.toggleGroup.getToggles().add(toggleButton);

		return toggleButton;
	}

	private NodeFx createNode(int x, int y) {
		NodeDialog nodeDialog = new NodeDialog();
		NodeFx nodeFx = null;
		
		if (nodeDialog.getNode() != null) {
			nodeFx = new NodeFx(nodeDialog.getNode());
			nodeFx.setX(x);
			nodeFx.setY(y);
			nodeFx.setWidth(50);
			nodeFx.setHeight(50);
			nodeFx.setFill(Color.GREEN);
			nodeFx.addNodeFxObserver(this);
			
			listeNoeud.add(nodeFx);
			mainContent.getChildren().add(nodeFx);
		}
		
		return nodeFx;
	}
	
	public void onNodeFXClicked(NodeFx nodeFx, MouseEvent event) {	
		if(mode == Mode.SELECT) {
			if(event.getClickCount() == 2) {
				NodeDialog nodeDialog = new NodeDialog(nodeFx.getNode());
				
				// TODO sauvegarder la modification sur le noeud
			}
		} else if(mode == Mode.ADD_NODE_LINK) {
			if(this.firstNodeFxSelected == null) {
				this.firstNodeFxSelected = nodeFx;
			} else {				
				NodeLinkDialog nodeLinkDialog = new NodeLinkDialog();
				BookNodeLink bookNodeLink = nodeLinkDialog.getNodeLink();
				bookNodeLink.setDestination(firstNodeFxSelected.getNode());
				// TODO vérifier si on a bien validé

				this.firstNodeFxSelected = null;
			} 		
		} else if(mode == Mode.SUPPRIMER) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Suppression");
			alert.setHeaderText("Voulez vous vraiment supprimer ?");

			Optional<ButtonType> choix = alert.showAndWait();
			if(choix.get() == ButtonType.OK){
				mainContent.getChildren().remove(nodeFx);
			}
		}

		event.consume();
	}
}
