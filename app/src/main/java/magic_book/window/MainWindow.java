package magic_book.window;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import magic_book.window.pane.GraphPane;
import magic_book.window.pane.LeftPane;
import magic_book.window.pane.RightPane;
import magic_book.window.gui.NodeFx;

/**
 * Fenêtre contenant le tout les Pane (LeftPane, GraphPane, RightPane)
 */
public class MainWindow extends Stage {

	/**
	 * Contient le centre de la fenêtre
	 */
	private GraphPane graphPane;
	/**
	 * Contient la partie gauche de la fenêtre
	 */
	private LeftPane leftPane;
	/**
	 * Contient la partie droite de la fenêtre
	 */
	private RightPane rightPane;
	
	/**
	 * Constitue la fenêtre
	 */
	private BorderPane root;
	
	/**
	 * Chemin du fichier actuellement ouvert
	 */
	private String path = null;
	
	/**
	 * Le livre contenant toutes les informations
	 */
	private Book book;
	

	/**
	 * Initialisation de la fenêtre principale
	 */
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

	/**
	 * Initialisation de la barre de menu
	 * @return Barre de menu
	 */
	private MenuBar createMenuBar() {
		MenuBar menuBar = new MenuBar();

		// --- Menu fichier
		Menu menuFile = new Menu("Fichier");
		MenuItem menuFileNew = new MenuItem("Nouveau");
		menuFileNew.setOnAction((ActionEvent e) -> {
			setBook(new Book());
			path = null;
		});
		
		//Permet d'ouvrir un livre
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

			//Si il y a une erreur dans l'ouverture du fichier
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
		
		//Permet d'enregistrer un livre
		MenuItem menuFileSave = new MenuItem("Enregistrer");
		menuFileSave.setOnAction((ActionEvent e) -> {
			if(path == null) {
				if(!changeSelectedFile())
					return;
			}
			
			saveFile();
		});
		
		//Permet d'enregistrer sous un livre
		MenuItem menuFileSaveAs = new MenuItem("Enregistrer sous");
		menuFileSaveAs.setOnAction((ActionEvent e) -> {
			if(!changeSelectedFile())
				return;
			
			saveFile();			
		});

		menuFile.getItems().addAll(menuFileNew, menuFileOpen, menuFileSave, menuFileSaveAs);

		// --- Menu livre
		Menu menuBook = new Menu("Livre");
		
		//Permet de jouer au livre
		MenuItem menuBookJouer = new MenuItem("Jouer");
		//Si erreur dans le chargement du livre
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
		
		//Permet de générer des fourmis afin d'estimé la difficulté
		MenuItem menuBookDifficulty = new MenuItem("Estimer la difficulté");
		//Si erreur dans le chargement du livre
		menuBookDifficulty.setOnAction((ActionEvent e) -> {
			try {
				Jeu jeu = new Jeu(book);
				float difficulte = jeu.fourmis(5000);
				rightPane.difficultyChanged(difficulte);
			} catch (IOException | BookFileException ex) {
				Alert a = new Alert(Alert.AlertType.ERROR);
				a.setTitle("Erreur lors de l'estimation");
				a.setHeaderText("Impossible d'estimer la difficulté du livre");
				a.show();
			}
		});
		
		//Permet de générer tout le livre en texte dans un format .txt
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
		//Permet de masquer/afficher le LeftPane
		Menu menuShow = new Menu("Affichage");
		CheckMenuItem menuShowLeftPanel = new CheckMenuItem("Mode, Items et personnages");
		menuShowLeftPanel.setOnAction((ActionEvent e) -> {
			if(menuShowLeftPanel.isSelected()){
				root.setLeft(leftPane);
			} else {
				root.setLeft(null);
			}
		});

		//Permet de masquer/afficher le RightPane
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
	
	/**
	 * Met à jour le livre lors de l'ouverture d'un fichier (nouveau au préexistant)
	 * @param book Nouveau livre
	 */
	private void setBook(Book book) {		
		this.book = book;
		
		leftPane.setBook(book);
		rightPane.setBook(book);
		graphPane.setBook(book);
	}
	
	/**
	 * Chemin du fichier
	 * @return Si l'enregistrement du fichier a été fait ou non
	 */
	private boolean changeSelectedFile() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Sauvegarder le livre");

		File selectedFile = fileChooser.showSaveDialog(this);
		if(selectedFile == null)
			return false;
		
		path = selectedFile.getAbsolutePath();
		
		return true;
	}
	
	/**
	 * Permet de souvegarder le doccument en fonction du path (chemin du fichier)
	 */
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