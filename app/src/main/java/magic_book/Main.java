package magic_book;

import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;

import magic_book.core.Book;
import magic_book.core.file.BookReader;
import magic_book.core.utils.BookGenerator;
import magic_book.core.utils.Fourmi;

import magic_book.window.MainWindow;

public class Main extends Application {

	@Override
	public void start(Stage stage) throws FileNotFoundException, IOException {
		new MainWindow();
	}
	
	public static void main(String[] args) {
		launch();
	}
	
}
