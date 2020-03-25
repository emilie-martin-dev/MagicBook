package magic_book.window.component;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import magic_book.core.Book;
import magic_book.core.graph.node.AbstractBookNode;
import magic_book.observer.book.BookNodeObserver;

public class RightPane extends ScrollPane implements BookNodeObserver {
	
	private Book book;
	
	private int nodeCount;
	private Label nodeCountLabel;
	
	public RightPane(Book book){		
		nodeCount = 0;
		
		this.setMaxWidth(250);
		this.setWidth(250d);
		this.setPadding(new Insets(5, 5, 5, 5));
		this.setFitToWidth(true);
				
		this.setContent(createRightPanel());
		
		setBook(book);
	}
	
	private Node createRightPanel() {
		HBox nodeCountBox = new HBox();
		Label prefixNodeCountLabel = new Label("Total de noeuds : ");
		nodeCountLabel = new Label(""+nodeCount);
		nodeCountBox.getChildren().addAll(prefixNodeCountLabel, nodeCountLabel);

		VBox statsLayout = new VBox();
		statsLayout.getChildren().addAll(nodeCountBox);

		return statsLayout;
	}
	
	private void updateStats() {
		nodeCountLabel.setText(""+nodeCount);
	}

	@Override
	public void nodeAdded(AbstractBookNode node) {
		nodeCount++;
		
		updateStats();
	}

	@Override
	public void nodeDeleted(AbstractBookNode node) {
		nodeCount--;
	
		updateStats();
	}

	@Override
	public void nodeEdited(AbstractBookNode oldNode, AbstractBookNode newNode) {
	
		updateStats();
	}
	
	public void setBook(Book book) {
		if(this.book != null)
			this.book.removeNodeObserver(this);
		
		this.book = book;
		this.book.addNodeObserver(this);
		
		nodeCount = this.book.getNodes().size();
		updateStats();
	}

	public Book getBook() {
		return book;
	}

}
