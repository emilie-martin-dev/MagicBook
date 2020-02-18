package magic_book;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import javafx.application.Application;
import javafx.stage.Stage;
import magic_book.core.file.json.BookJson;

import magic_book.window.MainWindow;

public class Main extends Application {

	@Override
	public void start(Stage stage) throws FileNotFoundException {
		GsonBuilder builder = new GsonBuilder(); 
		builder.setPrettyPrinting(); 

		Gson gson = builder.create(); 
		BufferedReader bufferedReader = new BufferedReader(new FileReader("livres/livre.json"));   
		BookJson book = gson.fromJson(bufferedReader, BookJson.class); 
	  
		new MainWindow();
	}
	
	public static void main(String[] args) {
		launch();
	}

}
