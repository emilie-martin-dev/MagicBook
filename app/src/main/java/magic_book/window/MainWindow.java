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

import magic_book.core.node.BookNodeLink;
import magic_book.core.file.BookTextExporter;
import magic_book.observer.NodeFxObserver;
import magic_book.observer.NodeLinkFxObserver;
import magic_book.window.dialog.NodeDialog;
import magic_book.window.dialog.NodeLinkDialog;
import magic_book.window.gui.NodeFx;
import magic_book.window.gui.NodeLinkFx;

public class MainWindow extends Stage implements NodeFxObserver, NodeLinkFxObserver {

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
				
				BookTextExporter.generateBook(firstNodeFxSelected.getNode(), new ArrayList<>(), new ArrayList<>(), selectedFile.getAbsolutePath());
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
		ToggleButton selectToogle = createToggleButton("Selectionner", Mode.SELECT);
		ToggleButton addNodeToggle = createToggleButton("Ajouter noeud", Mode.ADD_NODE);
		ToggleButton addNodeLinkToggle = createToggleButton("Ajouter lien", Mode.ADD_NODE_LINK);
		ToggleButton suppNode = createToggleButton("Supprime Noeud", Mode.DELETE);
		
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
		nodeFx.setWidth(50);
		nodeFx.setHeight(50);
		nodeFx.setFill(Color.GREEN);
		nodeFx.addNodeFxObserver(this);

		listeNoeud.add(nodeFx);
		mainContent.getChildren().add(nodeFx);
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
	
	public void onNodeFXClicked(NodeFx nodeFx, MouseEvent event) {	
		if(mode == Mode.SELECT) {
			this.firstNodeFxSelected = nodeFx;
			
			if(event.getClickCount() == 2) {
				new NodeDialog(nodeFx.getNode());
			}
		} else if(mode == Mode.ADD_NODE_LINK) {
			if(this.firstNodeFxSelected == null) {
				this.firstNodeFxSelected = nodeFx;
			} else {				
				NodeLinkDialog nodeLinkDialog = new NodeLinkDialog();
				BookNodeLink bookNodeLink = nodeLinkDialog.getNodeLink();
				
				if(bookNodeLink != null) {
					createNodeLink(bookNodeLink, firstNodeFxSelected, nodeFx);
				}
				
				this.firstNodeFxSelected = null;
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

	@Override
	public void onNodeLinkFXClicked(NodeLinkFx nodeLinkFx, MouseEvent event) {
		if(mode == Mode.SELECT) {
			if(event.getClickCount() == 2) {
				new NodeLinkDialog(nodeLinkFx.getNodeLink());
			}
		} 
	}
}
