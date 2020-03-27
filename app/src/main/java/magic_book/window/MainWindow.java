package magic_book.window;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
import magic_book.core.game.BookCharacter;
import magic_book.core.game.BookState;
import magic_book.core.game.player.Jeu;
import magic_book.core.graph.node.AbstractBookNode;
import magic_book.core.item.BookItemWeapon;
import magic_book.window.component.GraphPane;
import magic_book.window.component.LeftPane;
import magic_book.window.component.RightPane;
import magic_book.window.gui.NodeFx;

public class MainWindow extends Stage{

	private GraphPane graphPane;
	private LeftPane leftPane;
	private RightPane rightPane;
	
	private CheckMenuItem menuShowItemsCharacters;
	private String path = null;
	
	private Book book;
	private BookState state;
	
	public MainWindow() {
		book = book;
		state = state;
		
		BorderPane root = new BorderPane();
		graphPane = new GraphPane(book);
		leftPane = new LeftPane(graphPane, book);
		rightPane = new RightPane(graphPane, book);
		
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
		MenuItem testFileNew = new MenuItem("Test");
		testFileNew.setOnAction((ActionEvent e) -> {
					try{
						BookReader reader = new BookReader();
						book = reader.read("./test_aure");
					}
					catch(FileNotFoundException ex) {
						Alert a = new Alert(Alert.AlertType.ERROR);
						a.setTitle("Erreur lors de l'ouverture du fichier");
						a.setHeaderText("Le fichier n'existe pas");
						a.show(); 
					} catch (BookFileException ex) {
						Alert a = new Alert(Alert.AlertType.ERROR);
						a.setTitle("Erreur lors de l'ouverture du fichier");
						a.setHeaderText("Le fichier n'est pas bien formé");
						a.setContentText(ex.getMessage());
						a.show();
					}
					state = new BookState();
					AbstractBookNode bookNode = book.getRootNode();
					BookCharacter bookCharacter = new BookCharacter("", "Fourmis", 20, 100, null, null, null, 100, false);
					state.setMainCharacter(bookCharacter);
					BookItemWeapon arme = new BookItemWeapon("arme","La meilleur arme :) ", 5, 3);
					List<String> items = new ArrayList();
					items.add(arme.getId());
					book.getItems().put(arme.getId(), arme);
					state.getMainCharacter().setItems(items);
			Jeu jeu = new Jeu(state , book);
			System.out.println(jeu.fourmis(10000));
			
			jeu.play();
			System.out.println(jeu.fourmis(10000));
		});
		
		
		
		Menu menuFile = new Menu("Fichier");
		MenuItem menuFileNew = new MenuItem("Nouveau");
		menuFileNew.setOnAction((ActionEvent e) -> {
			changeBook(new Book());
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
				changeBook(book);
				
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

		menuFile.getItems().addAll(testFileNew, menuFileNew, menuFileOpen, menuFileSave, menuFileSaveAs);

		// --- Menu livre
		Menu menuBook = new Menu("Livre");
		MenuItem menuBookDifficulty = new MenuItem("Estimer la difficulté");
		menuBookDifficulty.setOnAction((ActionEvent e) -> {
			System.out.println("magic_book.window.MainWindow.createMenuBar()");
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
				BookTextExporter.generateBook(firstNodeFx.getNode(), new ArrayList<>(), new ArrayList<>(), selectedFile.getAbsolutePath());
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
	
	private void changeBook(Book book) {
		graphPane.setBook(book);
		leftPane.setBook(book);
	
		this.book = book;
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