package magic_book;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import magic_book.core.node.Node;
import magic_book.core.node.NodeType;
import magic_book.window.graph.NodeDialog;
import magic_book.window.graph.NodeLinkDialog;

public class Main extends Application {

	public void start(Stage stage) {
		//stage.show();
		//Node node = new Node("test",NodeType.FAILURE,null);
		//NodeDialog dialog = new NodeDialog();
		//NodeDialog dialog = new NodeDialog(node);
		//System.out.println("Le texte creer : "+node);
		
		NodeLinkDialog linkDialog = new NodeLinkDialog();
		System.out.println("Le texte creer : "+linkDialog);
		}
	
	public static void main(String[] args) {
		launch();
	}

}
