package magic_book;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import magic_book.core.node.Node;
import magic_book.core.node.NodeType;
import magic_book.window.MainWindow;
import magic_book.window.graph.NodeDialog;
import magic_book.window.graph.NodeLinkDialog;


public class Main extends Application {
        @Override
	public void start(Stage stage) {
		//new NodeDialog();
		
        new MainWindow();
		}
	
	public static void main(String[] args) {
		launch();
	}

}
