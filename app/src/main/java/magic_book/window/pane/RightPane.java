package magic_book.window.pane;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import magic_book.core.Book;
import magic_book.core.graph.node.AbstractBookNode;
import magic_book.observer.book.BookNodeObserver;
import magic_book.window.UiConsts;

public class RightPane extends ScrollPane implements BookNodeObserver{
	
	private Book book;
	
	private int nodeCount;
	private Label nodeCountLabel;
	
	private Label difficultePourcentageLabel;
	
	public RightPane(Book book){		
		nodeCount = 0;
		
		this.setMaxWidth(UiConsts.RIGHT_PANEL_SIZE);
		this.setMinWidth(UiConsts.RIGHT_PANEL_SIZE);
		this.setPadding(UiConsts.DEFAULT_INSET);
		this.setFitToWidth(true);
				
		this.setContent(createRightPanel());
		
		setBook(book);
	}
	
	private Node createRightPanel() {
		HBox nodeCountBox = new HBox();
		Label prefixNodeCountLabel = new Label("Total de noeuds : ");
		nodeCountLabel = new Label(""+nodeCount);
		nodeCountBox.getChildren().addAll(prefixNodeCountLabel, nodeCountLabel);
		
		HBox difficulteBox = new HBox();
		Label difficulteLabel = new Label("Difficulte du livre : ");
		difficultePourcentageLabel = new Label("?");
		difficulteBox.getChildren().addAll(difficulteLabel, difficultePourcentageLabel);
		
		VBox statsLayout = new VBox();
		statsLayout.getChildren().addAll(nodeCountBox, difficulteBox);

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
	
	public void difficultyChanged(float difficulte) {
		difficultePourcentageLabel.setText(""+difficulte);
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