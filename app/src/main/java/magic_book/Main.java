package magic_book;

import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;

import magic_book.core.Book;
import magic_book.core.file.BookReader;
import magic_book.core.file.BookTextExporter;
import magic_book.core.game.player.Fourmi;

import magic_book.window.MainWindow;

public class Main extends Application {

	@Override
	public void start(Stage stage) throws FileNotFoundException, IOException {
		Book book = BookReader.read("livres/livre.json");
		BookTextExporter.generateBook(book.getRootNode(), book.getItems(), book.getCharacters(), "build/livre");
		System.out.println("Difficulté : " + (100-Fourmi.estimerDifficulteLivre(book.getRootNode(), 10000)) + "%");
		
		new MainWindow();
	}
	
	public static void main(String[] args) {
		launch();
	}
	
}
