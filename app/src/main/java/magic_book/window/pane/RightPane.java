package magic_book.window.pane;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import magic_book.core.Book;
import magic_book.core.graph.node.AbstractBookNode;
import magic_book.core.graph.node.BookNodeCombat;
import magic_book.core.graph.node.BookNodeTerminal;
import magic_book.core.graph.node.BookNodeWithChoices;
import magic_book.core.graph.node.BookNodeWithRandomChoices;
import magic_book.observer.book.BookNodeObserver;
import magic_book.window.UiConsts;

public class RightPane extends ScrollPane implements BookNodeObserver{
	
	private Book book;
	
	private int nodeCount;
	private int nodeTerminalCount;
	private int nodeRandomCount;
	private int nodeChoiceCount;
	private int nodeCombatCount;
	
	private Label nodeCountLabel;
	private Label nodeTerminalCountLabel;
	private Label nodeChoiceCountLabel;
	private Label nodeRandomCountLabel;
	private Label nodeCombatCountLabel;
	private Label difficultePourcentageLabel;
	
	public RightPane(Book book){		
		nodeCount = 0;
		nodeTerminalCount = 0;
		nodeRandomCount = 0;
		nodeChoiceCount = 0;
		nodeCombatCount = 0;
		
		this.setMaxWidth(UiConsts.RIGHT_PANEL_SIZE);
		this.setMinWidth(UiConsts.RIGHT_PANEL_SIZE);
		this.setPadding(UiConsts.DEFAULT_INSET);
		this.setFitToWidth(true);
				
		this.setContent(createRightPanel());
		
		setBook(book);
	}
	
	private Node createRightPanel() {
		HBox nodeCountBox = new HBox();
		nodeCountLabel = new Label("");
		nodeCountBox.getChildren().addAll(new Label("Total de noeuds : "), nodeCountLabel);
		
		HBox nodeTerminalCountBox = new HBox();
		nodeTerminalCountLabel = new Label("");
		nodeTerminalCountBox.getChildren().addAll(new Label("Noeuds terminaux : "), nodeTerminalCountLabel);
		
		HBox nodeChoiceCountBox = new HBox();
		nodeChoiceCountLabel = new Label("");
		nodeChoiceCountBox.getChildren().addAll(new Label("Noeuds avec choix : "), nodeChoiceCountLabel);
		
		HBox nodeRandomCountBox = new HBox();
		nodeRandomCountLabel = new Label("");
		nodeRandomCountBox.getChildren().addAll(new Label("Noeuds aléatoires : "), nodeRandomCountLabel);
		
		HBox nodeCombatCountBox = new HBox();
		nodeCombatCountLabel = new Label("");
		nodeCombatCountBox.getChildren().addAll(new Label("Noeuds combats : "), nodeCombatCountLabel);
		
		HBox difficulteBox = new HBox();
		difficultePourcentageLabel = new Label("?");
		difficulteBox.getChildren().addAll(new Label("Difficulte du livre : "), difficultePourcentageLabel);
		
		VBox statsLayout = new VBox();
		statsLayout.setSpacing(UiConsts.DEFAULT_MARGIN);
		statsLayout.getChildren().addAll(nodeCountBox, nodeChoiceCountBox, nodeRandomCountBox, nodeCombatCountBox, difficulteBox);
		
		updateStats();

		return statsLayout;
	}
	
	
	private void updateStats() {
		nodeCountLabel.setText(""+nodeCount);
		nodeTerminalCountLabel.setText(""+nodeTerminalCount);
		nodeChoiceCountLabel.setText(""+nodeChoiceCount);
		nodeRandomCountLabel.setText(""+nodeRandomCount);
		nodeCombatCountLabel.setText(""+nodeCombatCount);
	}
	
	private void addToCounter(AbstractBookNode node) {
		if(node instanceof BookNodeTerminal) {
			nodeTerminalCount++;
		} else if(node instanceof BookNodeCombat) {
			nodeCombatCount++;
		} else if(node instanceof BookNodeWithRandomChoices) {
			nodeRandomCount++;
		} else if(node instanceof BookNodeWithChoices) {
			nodeChoiceCount++;
		}
		
		nodeCount++;
	}
	
	private void removeToCounter(AbstractBookNode node) {
		if(node instanceof BookNodeTerminal) {
			nodeTerminalCount--;
		} else if(node instanceof BookNodeCombat) {
			nodeCombatCount--;
		} else if(node instanceof BookNodeWithRandomChoices) {
			nodeRandomCount--;
		} else if(node instanceof BookNodeWithChoices) {
			nodeChoiceCount--;
		}
		
		nodeCount--;
	}

	@Override
	public void nodeAdded(AbstractBookNode node) {
		addToCounter(node);
		
		updateStats();
	}

	@Override
	public void nodeDeleted(AbstractBookNode node) {
		removeToCounter(node);
	
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
		
		nodeTerminalCount = 0;
		nodeCombatCount = 0;
		nodeRandomCount = 0;
		nodeChoiceCount = 0;
		nodeCount = 0;
		
		this.book = book;
		this.book.addNodeObserver(this);
		
		for(AbstractBookNode node : book.getNodes().values()) {
			addToCounter(node);
		}
		
		updateStats();
	}

	public Book getBook() {
		return book;
	}

}
