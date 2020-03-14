package magic_book.window;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.event.ActionEvent;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import magic_book.core.Book;
import magic_book.core.file.BookReader;
import magic_book.core.node.BookNode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import magic_book.core.BookCharacter;
import magic_book.core.item.Arme;
import magic_book.core.item.BookItem;

import magic_book.core.node.BookNodeLink;
import magic_book.core.utils.BookGenerator;
import magic_book.observer.NodeLinkFxObserver;
import magic_book.window.dialog.NodeDialog;
import magic_book.window.dialog.NodeLinkDialog;
import magic_book.window.gui.NodeFx;
import magic_book.window.gui.NodeLinkFx;
import magic_book.observer.RectangleFxObserver;
import magic_book.window.dialog.PreludeDialog;
import magic_book.window.gui.PreludeFx;
import magic_book.window.gui.RectangleFx;

public class MainWindow extends Stage implements NodeLinkFxObserver {

	private Mode mode;
	private ToggleGroup toggleGroup;
	
	private List<NodeFx> listeNoeud;
	
	private NodeFx firstNodeFxSelected;
	
	private Pane mainContent;
	
	private PreludeFx preludeFx;

	public MainWindow() {
		BorderPane root = new BorderPane();

		listeNoeud = new ArrayList<>();
		
		mainContent = new Pane();
		mainContent.setStyle("-fx-background-color: #dddddd;");
		mainContent.setCursor(Cursor.CLOSED_HAND);
		mainContent.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
			if (mode == Mode.ADD_NODE) {
				NodeDialog nodeDialog = new NodeDialog();
				BookNode node = nodeDialog.getNode();
				if(node != null) {
					createNode(node, (int) event.getX(), (int) event.getY());
				}
			} else if (mode == Mode.SELECT) {

			} else if (mode == Mode.ADD_NODE_LINK) {

			}
		});
		
		root.setTop(createMenuBar());
		root.setLeft(createLeftPanel());
		root.setCenter(mainContent);
		
		createNodePrelude();
		
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
		menuFileOpen.setOnAction((ActionEvent e) -> {
			try {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Ouvrir un livre");
				fileChooser.getExtensionFilters().addAll(
					new ExtensionFilter("Livre", "*.mbf"),
					new ExtensionFilter("JSON", "*.json"),
					new ExtensionFilter("Tous les fichiers", "*.*"));
				
				File selectedFile = fileChooser.showOpenDialog(this);
				if (selectedFile == null) {
					return;
				}
				
				Book book = BookReader.read(selectedFile.getAbsolutePath());
				
				for(BookNode node : book.getNodes().values()) {					
					createNode(node, 0, 0);
				}
				
				for(BookNode node : book.getNodes().values()) {
					NodeFx first = null;
					for(NodeFx fx : listeNoeud) {
						if(fx.getNode() == node) {
							first = fx;
							break;
						}
					}
						
					for(BookNodeLink choice : node.getChoices()) {
						NodeFx second = null;
						for(NodeFx fx : listeNoeud) {
							if(fx.getNode() == choice.getDestination()) {
								second = fx;
								break;
							}
						}

						createNodeLink(choice, first, second);
					}
				}

			} catch (FileNotFoundException ex) {
				Alert a = new Alert(AlertType.ERROR);
				a.setTitle("Erreur lors de l'ouverture du fichier");
				a.setHeaderText("Le fichier n'existe pas");
				a.show(); 
			}
		});
		MenuItem menuFileSave = new MenuItem("Enregistrer");
		MenuItem menuFileSaveAs = new MenuItem("Enregistrer sous");

		menuFile.getItems().addAll(menuFileNew, menuFileOpen, menuFileSave, menuFileSaveAs);

		// --- Menu livre
		Menu menuBook = new Menu("Livre");
		MenuItem menuBookDifficulty = new MenuItem("Estimer la difficulté");
		MenuItem menuBookGenerate = new MenuItem("Générer le livre");
		menuBookGenerate.setOnAction((ActionEvent e) -> {
			try {
				if(firstNodeFxSelected == null) {
					Alert a = new Alert(AlertType.WARNING);
					a.setTitle("Erreur lors de l'export");
					a.setHeaderText("Merci de sélectionner au préalable le noeud de départ");
					a.show(); 
					return;
				}
				
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Exporter en texte");
				
				File selectedFile = fileChooser.showSaveDialog(this);
				if (selectedFile == null) {
					return;
				}
				
				BookGenerator.generateBook(firstNodeFxSelected.getNode(), new ArrayList<>(), new ArrayList<>(), selectedFile.getAbsolutePath());
			} catch (IOException ex) {
				Alert a = new Alert(AlertType.ERROR);
				a.setTitle("Erreur lors de l'export du fichier");
				a.setHeaderText("Impossible de sauvegarder le fichier sur le disque");
				a.show(); 
			}
		});

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
		VBox leftContent = new VBox();
		
		ToggleButton selectToogle = createToggleButton("Selectionner", Mode.SELECT);
		ToggleButton addNodeToggle = createToggleButton("Ajouter noeud", Mode.ADD_NODE);
		ToggleButton addNodeLinkToggle = createToggleButton("Ajouter lien", Mode.ADD_NODE_LINK);
		ToggleButton suppNode = createToggleButton("Supprime Noeud", Mode.DELETE);
		
		selectToogle.setSelected(true);
		this.mode = Mode.SELECT;

		FlowPane flow = new FlowPane();
		flow.getChildren().addAll(selectToogle, addNodeToggle, addNodeLinkToggle, suppNode);
		leftContent.setMaxWidth(250);
		leftContent.setPadding(new Insets(5, 5, 5, 5));
		leftContent.setSpacing(15);
		leftContent.getChildren().add(flow);
		leftContent.getChildren().add(gestionPerso());
		
		return leftContent;
	}

	private ToggleButton createToggleButton(String text, Mode mode) {
		ToggleButton toggleButton = new ToggleButton(text);

		toggleButton.setOnAction((ActionEvent e) -> {
			MainWindow.this.mode = mode;
			this.firstNodeFxSelected = null;
		});

		toggleButton.setPrefSize(100, 100);

		if (this.toggleGroup == null) {
			this.toggleGroup = new ToggleGroup();
		}

		this.toggleGroup.getToggles().add(toggleButton);

		return toggleButton;
	}

	private void createNode(BookNode node, int x, int y) {
		NodeFx nodeFx = new NodeFx(node);
		nodeFx.setX(x);
		nodeFx.setY(y);
		nodeFx.setFill(Color.GREEN);
		nodeFx.addNodeFxObserver(new NodeFxListener());

		listeNoeud.add(nodeFx);
		mainContent.getChildren().add(nodeFx);
	}
	
	private void createNodePrelude() {
		PreludeFx preludeFx = new PreludeFx(null);
		preludeFx.setX(10);
		preludeFx.setY(10);
		
		preludeFx.addNodeFxObserver((RectangleFx rectangleFx, MouseEvent event) -> {
			if(mode == Mode.SELECT) {
				if(event.getClickCount() == 2) {
					PreludeDialog dialog = new PreludeDialog(this.preludeFx.getText());
					this.preludeFx.setText(dialog.getTextePrelude());
				}
			}
		});

		mainContent.getChildren().add(preludeFx);
		this.preludeFx = preludeFx;
	}
	
	public void createNodeLink(BookNodeLink bookNodeLink, NodeFx firstNodeFx, NodeFx secondNodeFx) {
		bookNodeLink.setDestination(secondNodeFx.getNode());
		
		if(!firstNodeFx.getNode().getChoices().contains(bookNodeLink))
			firstNodeFx.getNode().getChoices().add(bookNodeLink);

		NodeLinkFx nodeLinkFx = new NodeLinkFx(bookNodeLink, firstNodeFx, secondNodeFx);

		nodeLinkFx.startXProperty().bind(firstNodeFx.xProperty().add(firstNodeFx.widthProperty().divide(2)));
		nodeLinkFx.startYProperty().bind(firstNodeFx.yProperty().add(firstNodeFx.heightProperty().divide(2)));

		nodeLinkFx.endXProperty().bind(secondNodeFx.xProperty().add(secondNodeFx.widthProperty().divide(2)));
		nodeLinkFx.endYProperty().bind(secondNodeFx.yProperty().add(secondNodeFx.heightProperty().divide(2)));

		nodeLinkFx.addNodeLinkFxObserver(this);
		mainContent.getChildren().add(nodeLinkFx);
	}

	@Override
	public void onNodeLinkFXClicked(NodeLinkFx nodeLinkFx, MouseEvent event) {
		if(mode == Mode.SELECT) {
			if(event.getClickCount() == 2) {
				new NodeLinkDialog(nodeLinkFx.getNodeLink());
			}
		}
	}
	
	private VBox gestionPerso(){
		TreeItem<BookCharacter> rootPerso = new TreeItem<> (new BookCharacter("0", "Personnage", "", 0, 0, null, null, 0));
		rootPerso.setExpanded(true);
		TreeItem<BookCharacter> Perso1 = new TreeItem<> (new BookCharacter("0", "Robert", "Humain", 0, 0, null, null, 0));
		TreeItem<BookCharacter> Perso2 = new TreeItem<> (new BookCharacter("1", "Didier", "Goblin", 0, 0, null, null, 0));
		rootPerso.getChildren().addAll(Perso1, Perso2);
		TreeView<BookCharacter> treeView = new TreeView<> (rootPerso);
		
		TreeItem<BookItem> rootItem = new TreeItem<> (new BookItem("0","Item"));
		rootPerso.setExpanded(true);
		TreeItem<BookItem> Item1 = new TreeItem<> (new Arme("1","Epée",5));
		TreeItem<BookItem> Item2 = new TreeItem<> (new BookItem("2","Bouclier"));
		rootItem.getChildren().addAll(Item1, Item2);
		TreeView<BookItem> treeView2 = new TreeView<> (rootItem);
		
		
		VBox vBox = new VBox(10, treeView,treeView2);
		vBox.setSpacing(5);
		
		return vBox;
		
		
	}

	class NodeFxListener implements RectangleFxObserver {
		
		public void onRectangleFXClicked(RectangleFx rectangleFx, MouseEvent event) {	
			NodeFx nodeFx = (NodeFx) rectangleFx;
			if(mode == Mode.SELECT) {
				MainWindow.this.firstNodeFxSelected = nodeFx;

				if(event.getClickCount() == 2) {
					new NodeDialog(nodeFx.getNode());
				}
			} else if(mode == Mode.ADD_NODE_LINK) {
				if(MainWindow.this.firstNodeFxSelected == null) {
					MainWindow.this.firstNodeFxSelected = nodeFx;
				} else {				
					NodeLinkDialog nodeLinkDialog = new NodeLinkDialog();
					BookNodeLink bookNodeLink = nodeLinkDialog.getNodeLink();

					if(bookNodeLink != null) {
						createNodeLink(bookNodeLink, firstNodeFxSelected, nodeFx);
					}

					MainWindow.this.firstNodeFxSelected = null;
				} 		
			} else if(mode == Mode.DELETE) {
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
	
}
	

