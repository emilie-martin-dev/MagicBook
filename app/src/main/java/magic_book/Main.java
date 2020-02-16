package magic_book;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.stage.Stage;
import magic_book.core.node.BookNode;
import magic_book.core.node.BookNodeLink;
import magic_book.core.node.BookNodeType;
import magic_book.utils.BookGenerator;
import magic_book.window.MainWindow;

public class Main extends Application {

	@Override
	public void start(Stage stage) {
		BookNode n4 = new BookNode("Vous sentez le pain chaud et les croissants.", BookNodeType.VICTORY, null);
		BookNode n3 = new BookNode("Vous vous êtes perdu...", BookNodeType.FAILURE, null);
		BookNode n2 = new BookNode("Vous apercevez au loin ce qui semble être un magasin", BookNodeType.BASIC, new ArrayList(Arrays.asList(new BookNodeLink("Allez voir", n4))));
		BookNode r = new BookNode("Vous êtes dans un cul de sac", BookNodeType.BASIC, Arrays.asList(new BookNodeLink("Faire demi tour", n2)));
		n2.getChoices().add(new BookNodeLink("Allez à gauche", r));
		BookNode n1 = new BookNode("Vous avez faim, vous cherchez une boulangerie.", BookNodeType.BASIC, Arrays.asList(new BookNodeLink("Tourner à droite", n2), new BookNodeLink("Aller tout droit", n3)));
		
		try {
			BookGenerator.generateBook(n1, new ArrayList<>(), new ArrayList<>(), "test.livre");
		} catch (IOException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		new MainWindow();
	}
	public static void main(String[] args) {
		launch();
	}

}
