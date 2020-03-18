package magic_book.window.component;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import magic_book.core.Book;

public class RightPane extends Pane {
	
	private GraphPane graphPane;
	private Book book;
	
	public RightPane(GraphPane graphPane, Book book){
		this.graphPane = graphPane;
		this.book = book;
		
		this.getChildren().add(createRightPanel());
	}
	
	private Node createRightPanel() {
		//Label prefixLabelNodeCount = new Label("Total de noeuds : " + this.listeNoeud.size());

		VBox statsLayout = new VBox();
		//statsLayout.getChildren().addAll(labelNodeCount);

		return statsLayout;
	}
}
