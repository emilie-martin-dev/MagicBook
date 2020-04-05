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

/**
 * Partie droite de la MainWindows (statistiques du livre)
 */
public class RightPane extends ScrollPane implements BookNodeObserver{
	
	/**
	 * Livre avec toutes les informations
	 */
	private Book book;
	
	
	/**
	 * Nombre total de noeud
	 */
	private int nodeCount;
	
	/**
	 * Nombre total de noeud terminal
	 */
	private int nodeTerminalCount;
	
	/**
	 * Nombre total de noeud à choix random
	 */
	private int nodeRandomCount;
	
	/**
	 * Nombre total de noeud à choix
	 */
	private int nodeChoiceCount;
	
	/**
	 * Nombre total de noeud de combat
	 */
	private int nodeCombatCount;
	
	
	private Label nodeCountLabel;
	private Label nodeTerminalCountLabel;
	private Label nodeChoiceCountLabel;
	private Label nodeRandomCountLabel;
	private Label nodeCombatCountLabel;
	private Label difficultePourcentageLabel;
	
	
	/**
	 * Initialisation des valeurs qui compose le panel des statistiques
	 * @param book Le livre contenant toutes les informations
	 */
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
	
	/**
	 * Création du panel de statistiques
	 * @return VBox contenant toutes les statistiques
	 */
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
	
	/**
	 * Met à jour les labels des statistiques
	 */
	private void updateStats() {
		nodeCountLabel.setText(""+nodeCount);
		nodeTerminalCountLabel.setText(""+nodeTerminalCount);
		nodeChoiceCountLabel.setText(""+nodeChoiceCount);
		nodeRandomCountLabel.setText(""+nodeRandomCount);
		nodeCombatCountLabel.setText(""+nodeCombatCount);
	}
	
	/**
	 * Ajoute +1 au compteur, en fonction du type de noeud
	 * @param node Noeud ajouter
	 */
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
	
	/**
	 * Enlève 1 au compteur, en fonction du type de noeud
	 * @param node Noeud supprimer
	 */
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
	
	/**
	 * Change l'estimation de la difficulté
	 * @param difficulte Estimation de la difficulté
	 */
	public void difficultyChanged(float difficulte) {
		difficultePourcentageLabel.setText(""+difficulte);
	}	
	
	/**
	 * Remet toutes les statisques à zéro si on ouvre un nouveau livre
	 * @param book Livre contenant toutes les informations
	 */
	public void setBook(Book book) {
		if(this.book != null)
			this.book.removeNodeObserver(this);
		
		nodeTerminalCount = 0;
		nodeCombatCount = 0;
		nodeRandomCount = 0;
		nodeChoiceCount = 0;
		nodeCount = 0;
		difficultePourcentageLabel.setText("?");
		
		this.book = book;
		this.book.addNodeObserver(this);
		
		for(AbstractBookNode node : book.getNodes().values()) {
			addToCounter(node);
		}
		
		updateStats();
	}

	/**
	 * Donne le livre
	 * @return Le livre avec toutes les informations
	 */
	public Book getBook() {
		return book;
	}

}
