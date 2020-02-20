package magic_book;

import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.stage.Stage;
import magic_book.core.Book;
import magic_book.core.file.BookReader;

import magic_book.window.MainWindow;

public class Main extends Application {

	@Override
	public void start(Stage stage) throws FileNotFoundException {
		Book book = BookReader.read("livres/livre.json");
		
		new MainWindow();
	}
	
	public static void main(String[] args) {
		launch();
	}

}
