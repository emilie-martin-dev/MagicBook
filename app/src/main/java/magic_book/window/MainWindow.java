package magic_book.window;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import magic_book.core.Book;
import magic_book.core.exception.BookFileException;
import magic_book.core.file.BookReader;
import magic_book.core.file.BookTextExporter;
import magic_book.core.file.BookWritter;
import magic_book.core.game.player.Jeu;
import magic_book.window.gui.NodeFx;
import magic_book.window.pane.GraphPane;
import magic_book.window.pane.LeftPane;
import magic_book.window.pane.RightPane;

public class MainWindow extends Stage {

	private GraphPane graphPane;
	private LeftPane leftPane;
	private RightPane rightPane;
	
	private BorderPane root;
	
	private String path = null;
	
	private Book book;
	

	public MainWindow() {
		book = new Book();
		
		root = new BorderPane();
		graphPane = new GraphPane(book);
		leftPane = new LeftPane(graphPane, book);
		rightPane = new RightPane(book);
		
		root.setTop(createMenuBar());
		root.setLeft(leftPane);
		root.setRight(rightPane);
		root.setCenter(graphPane);
		
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
		menuFileNew.setOnAction((ActionEvent e) -> {
			setBook(new Book());
			path = null;
		});
		MenuItem menuFileOpen = new MenuItem("Ouvrir");
		menuFileOpen.setOnAction((ActionEvent e) -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Ouvrir un livre");
			fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter("Livre", "*.mbf"),
				new ExtensionFilter("JSON", "*.json"),
				new ExtensionFilter("Tous les fichiers", "*"));

			File selectedFile = fileChooser.showOpenDialog(this);
			if (selectedFile == null) {
				return;
			}

			try {
				BookReader reader = new BookReader();
				Book book = reader.read(selectedFile.getAbsolutePath());
				setBook(book);
				
				path = selectedFile.getAbsolutePath();
			} catch (FileNotFoundException ex) {
				Alert a = new Alert(AlertType.ERROR);
				a.setTitle("Erreur lors de l'ouverture du fichier");
				a.setHeaderText("Le fichier n'existe pas");
				a.show(); 
			} catch (BookFileException ex) {
				Alert a = new Alert(AlertType.ERROR);
				a.setTitle("Erreur lors de l'ouverture du fichier");
				a.setHeaderText("Le fichier n'est pas bien formé");
				a.setContentText(ex.getMessage());
				a.show();
			}			
		});
		MenuItem menuFileSave = new MenuItem("Enregistrer");
		menuFileSave.setOnAction((ActionEvent e) -> {
			if(path == null) {
				if(!changeSelectedFile())
					return;
			}
			
			saveFile();
		});
		MenuItem menuFileSaveAs = new MenuItem("Enregistrer sous");
		menuFileSaveAs.setOnAction((ActionEvent e) -> {
			if(!changeSelectedFile())
				return;
			
			saveFile();			
		});

		menuFile.getItems().addAll(menuFileNew, menuFileOpen, menuFileSave, menuFileSaveAs);

		// --- Menu livre
		Menu menuBook = new Menu("Livre");
		
		MenuItem menuBookJouer = new MenuItem("Jouer");
		menuBookJouer.setOnAction((ActionEvent e) -> {
			try {
				Jeu jeu = new Jeu(book);
				jeu.play();
			} catch (IOException | BookFileException ex) {
				Alert a = new Alert(Alert.AlertType.ERROR);
				a.setTitle("Erreur lors du chargement du jeu");
				a.setHeaderText("Impossible de jouer au livre");
				a.show();
			}
		});
		
		MenuItem menuBookDifficulty = new MenuItem("Estimer la difficulté");
		menuBookDifficulty.setOnAction((ActionEvent e) -> {
			try {
				Jeu jeu = new Jeu(book);
				float difficulte = jeu.fourmis(1000);
				rightPane.difficultyChanged(difficulte);
			} catch (IOException | BookFileException ex) {
				Alert a = new Alert(Alert.AlertType.ERROR);
				a.setTitle("Erreur lors de l'estimation");
				a.setHeaderText("Impossible d'estimer la difficulté du livre");
				a.show();
			}
		});
		
		MenuItem menuBookGenerate = new MenuItem("Générer le livre en txt");
		menuBookGenerate.setOnAction((ActionEvent e) -> {
			NodeFx firstNodeFx = graphPane.getPreludeFx().getFirstNode();
			if(firstNodeFx == null) {
				Alert a = new Alert(Alert.AlertType.WARNING);
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
			
			try {
				BookTextExporter.generateBook(book, selectedFile.getAbsolutePath());
			} catch (IOException ex) {
				Alert a = new Alert(Alert.AlertType.ERROR);
				a.setTitle("Erreur lors de l'export du fichier");
				a.setHeaderText("Impossible de sauvegarder le fichier sur le disque");
				a.show(); 
			}
		});

		menuBook.getItems().addAll(menuBookJouer, menuBookDifficulty, menuBookGenerate);

		// --- Menu affichage
		Menu menuShow = new Menu("Affichage");
		CheckMenuItem menuShowLeftPanel = new CheckMenuItem("Mode, Items et personnages");
		menuShowLeftPanel.setOnAction((ActionEvent e) -> {
			if(menuShowLeftPanel.isSelected()){
				root.setLeft(leftPane);
			} else {
				root.setLeft(null);
			}
		});


		CheckMenuItem menuShowStatsPanel = new CheckMenuItem("Statistiques");
		menuShowStatsPanel.setOnAction((ActionEvent e) -> {
			if(menuShowStatsPanel.isSelected()) {
				root.setRight(rightPane);
			} else {
				root.setRight(null);
			}
		});

		menuShowLeftPanel.setSelected(true);
		menuShowStatsPanel.setSelected(true);

		menuShow.getItems().addAll(menuShowLeftPanel, menuShowStatsPanel);

		menuBar.getMenus().addAll(menuFile, menuBook, menuShow);

		return menuBar;
	}
	
	private void setBook(Book book) {		
		this.book = book;
		
		leftPane.setBook(book);
		rightPane.setBook(book);
		graphPane.setBook(book);
	}
	
	private boolean changeSelectedFile() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Sauvegarder le livre");

		File selectedFile = fileChooser.showSaveDialog(this);
		if(selectedFile == null)
			return false;
		
		path = selectedFile.getAbsolutePath();
		
		return true;
	}
	
	private void saveFile() {
		File saveFile = new File(path);
		
		if(!saveFile.getParentFile().exists())
			saveFile.mkdirs();
		
		BookWritter bookWritter = new BookWritter();
		try {
			bookWritter.write(path, book);
		} catch (IOException ex) {
			Alert a = new Alert(AlertType.ERROR);
			a.setTitle("Erreur lors de l'écriture du fichier");
			a.setHeaderText("Impossible d'écrire le fichier sur le disque");
			a.show(); 
		}
	}
	
}