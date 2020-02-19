package magic_book.window;

import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
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
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import magic_book.core.node.BookNode;
import magic_book.observer.ModeObservable;
import magic_book.observer.ModeObserver;
import magic_book.window.dialog.NodeDialog;
import magic_book.window.gui.Mode;
import magic_book.window.gui.NodeFx;

public class MainWindow extends Stage {

	private Mode mode;
	private ToggleGroup toggleGroup;
	
	private List<NodeFx> listeNoeud;
	
	private ModeObservable modeObservable;

	public MainWindow() {
		BorderPane root = new BorderPane();

		modeObservable = new ModeObservable();
		listeNoeud = new ArrayList<>();
		
		Pane mainContent = new Pane();
		mainContent.setCursor(Cursor.CLOSED_HAND);
		mainContent.addEventHandler(MouseEvent.MOUSE_PRESSED, (new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (mode == Mode.ADD_NODE) {
					NodeFx node = handleNodeCreation(event, mode);
					if (node != null){
						mainContent.getChildren().add(node);
					}
				}
				if (mode == Mode.SELECT) {
					modifANode(event, mode);
				}
			}
		}));

		
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
		
		selectToogle.setSelected(true);
		this.mode = Mode.SELECT;

		FlowPane flow = new FlowPane();
		flow.getChildren().addAll(selectToogle, addNodeToggle, addNodeLinkToggle);
		flow.setPadding(new Insets(5, 5, 5, 5));

		return flow;
	}

	private ToggleButton createToggleButton(String text, Mode mode) {
		ToggleButton toggleButton = new ToggleButton(text);

		toggleButton.setOnAction((ActionEvent e) -> {
			MainWindow.this.mode = mode;
			modeObservable.notifyModeChanged(mode);
		});

		toggleButton.setPrefSize(100, 100);

		if (this.toggleGroup == null) {
			this.toggleGroup = new ToggleGroup();
		}

		this.toggleGroup.getToggles().add(toggleButton);

		return toggleButton;
	}

	private NodeFx handleNodeCreation(MouseEvent event, Mode mode) {
		NodeDialog dialog = new NodeDialog();
		NodeFx rectangle = null;
		
		if (dialog.getNode() != null){
			rectangle = new NodeFx(dialog.getNode());
			modeObservable.addObserver(rectangle);
			listeNoeud.add(rectangle);
			rectangle.setX(event.getX());
			rectangle.setY(event.getY());
			rectangle.setWidth(50);
			rectangle.setHeight(50);
			rectangle.setFill(Color.GREEN);
		}
		
		return rectangle;
	}
	
	private void createANode(Node node) {
		
	}
	
	private void modifANode(MouseEvent event, Mode mode){
		event.consume();
		MouseEvent ev1 = event;
		System.out.println("clique select");

	}
}
