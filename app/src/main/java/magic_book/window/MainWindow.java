package magic_book.window;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

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
import magic_book.window.component.GraphPane;
import magic_book.window.component.LeftPane;
import magic_book.window.component.RightPane;

public class MainWindow extends Stage{

	private GraphPane graphPane;
	private LeftPane leftPane;
	private RightPane rightPane;
	
	public MainWindow() {
		BorderPane root = new BorderPane();
		graphPane = new GraphPane();
		leftPane = new LeftPane(graphPane);
		rightPane = new RightPane(graphPane);
		
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
		MenuItem menuFileOpen = new MenuItem("Ouvrir");
		menuFileOpen.setOnAction((ActionEvent e) -> {
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

			try {
				BookReader reader = new BookReader();
				Book book = reader.read(selectedFile.getAbsolutePath());
				graphPane.setBookNode(book);
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
		MenuItem menuFileSaveAs = new MenuItem("Enregistrer sous");

		menuFile.getItems().addAll(menuFileNew, menuFileOpen, menuFileSave, menuFileSaveAs);

		// --- Menu livre
		Menu menuBook = new Menu("Livre");
		MenuItem menuBookDifficulty = new MenuItem("Estimer la difficulté");
		MenuItem menuBookGenerate = new MenuItem("Générer le livre");
		menuBookGenerate.setOnAction((ActionEvent e) -> {
			try {
				if(graphPane.getFirstNodeFxSelected() == null) {
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
				
				BookTextExporter.generateBook(graphPane.getFirstNodeFxSelected().getNode(), new ArrayList<>(), new ArrayList<>(), selectedFile.getAbsolutePath());
			} catch (IOException ex) {
				Alert a = new Alert(Alert.AlertType.ERROR);
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

}