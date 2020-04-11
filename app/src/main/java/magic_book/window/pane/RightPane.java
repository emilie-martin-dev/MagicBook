package magic_book.window.pane;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import magic_book.core.Book;
import magic_book.core.graph.node.AbstractBookNode;
import magic_book.core.graph.node.BookNodeCombat;
import magic_book.core.graph.node.BookNodeStatus;
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
	 * Nombre total de noeud gagnants
	 */
	private int nodeVictoryCount;
		/**
	 * Nombre total de noeud perdants
	 */
	private int nodeFailureCount;
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
	
	/**
	 * Label pour afficher uniquement le compteur du nombre de noeud
	 */
	private Label nodeCountLabel;
	
	/**
	 * Label pour afficher uniquement le compteur du nombre de noeud gagnants
	 */
	private Label nodeVictoryCountLabel;
	
	/**
	 * Label pour afficher uniquement le compteur du nombre de noeud perdants
	 */
	private Label nodeFailureCountLabel;
	
	/**
	 * Label pour afficher uniquement le compteur du nombre de noeud à choix
	 */
	private Label nodeChoiceCountLabel;
	
	/**
	 * Label pour afficher uniquement le compteur du nombre de noeud à choix aléatoire
	 */
	private Label nodeRandomCountLabel;
	
	/**
	 * Label pour afficher uniquement le compteur du nombre de noeud de combat
	 */
	private Label nodeCombatCountLabel;
	
	/**
	 * Label pour afficher uniquement le compteur de la difficulté
	 */
	private Label difficultePourcentageLabel;
	
	/**
	 * Initialisation des valeurs qui compose le panel des statistiques
	 * @param book Le livre contenant toutes les informations
	 */
	public RightPane(Book book){		
		nodeCount = 0;
		nodeVictoryCount = 0;
		nodeFailureCount = 0;
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
		
		HBox nodeVictoryCountBox = new HBox();
		nodeVictoryCountLabel = new Label("");
		nodeVictoryCountBox.getChildren().addAll(new Label("Noeuds gagnants : "), nodeVictoryCountLabel);
		
		HBox nodeFailureCountBox = new HBox();
		nodeFailureCountLabel = new Label("");
		nodeFailureCountBox.getChildren().addAll(new Label("Noeuds perdants : "), nodeFailureCountLabel);
		
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
		statsLayout.getChildren().addAll(nodeCountBox, nodeChoiceCountBox, nodeRandomCountBox, nodeCombatCountBox, nodeVictoryCountBox, nodeFailureCountBox, difficulteBox);
		
		updateStats();

		return statsLayout;
	}
	
	/**
	 * Met à jour les labels des statistiques
	 */
	private void updateStats() {
		nodeCountLabel.setText(""+nodeCount);
		nodeVictoryCountLabel.setText(""+nodeVictoryCount);
		nodeFailureCountLabel.setText(""+nodeFailureCount);
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
			BookNodeTerminal bookNodeTerminal = (BookNodeTerminal) node;
			if(bookNodeTerminal.getBookNodeStatus() == BookNodeStatus.VICTORY) {
				nodeVictoryCount++;
			} else if (bookNodeTerminal.getBookNodeStatus() == BookNodeStatus.FAILURE) {
				nodeFailureCount++;
			}
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
			BookNodeTerminal bookNodeTerminal = (BookNodeTerminal) node;
			if(bookNodeTerminal.getBookNodeStatus() == BookNodeStatus.VICTORY) {
				nodeVictoryCount--;
			} else if (bookNodeTerminal.getBookNodeStatus() == BookNodeStatus.FAILURE) {
				nodeFailureCount--;
			}
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
		removeToCounter(oldNode);
		addToCounter(newNode);
		
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
		
		nodeVictoryCount = 0;
		nodeFailureCount = 0;
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
