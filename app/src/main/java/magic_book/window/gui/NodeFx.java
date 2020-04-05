package magic_book.window.gui;

import javafx.beans.property.FloatProperty;
import javafx.scene.paint.Color;
import magic_book.core.graph.node.AbstractBookNode;
import magic_book.core.graph.node.BookNodeCombat;
import magic_book.core.graph.node.BookNodeStatus;
import magic_book.core.graph.node.BookNodeTerminal;
import magic_book.core.graph.node.BookNodeWithChoices;
import magic_book.core.graph.node.BookNodeWithRandomChoices;

/**
 * Représente graphiquement un noeud du livre
 */
public class NodeFx extends RectangleFx {
	
	/**
	 * Noeud lié au rectangle
	 */
	private AbstractBookNode node;

	/**
	 * Initialisation du rectangle en fonction du type de noeud
	 * @param node Noeud rattaché
	 * @param zoom Valeur du zoom
	 */
	public NodeFx(AbstractBookNode node, FloatProperty zoom) {
		super(Color.GREEN, zoom);
		
		this.node = node;
		
		updateNodeColor();
	}

	/**
	 * Donne le noeud rattaché
	 * @return Noeud rattaché
	 */
	public AbstractBookNode getNode() {
		return node;
	}

	/**
	 * Modifie le noeud rattaché
	 * @param node Nouveau noeud à rattacher
	 */
	public void setNode(AbstractBookNode node) {
		this.node = node;
		
		updateNodeColor();
	}
	
	/**
	 * Modifie la couleur en fonction du type de noeud
	 */
	public void updateNodeColor(){
		if(node instanceof BookNodeCombat){
			setDefaultColor(Color.GOLD);
		} else if(node instanceof BookNodeTerminal){
			BookNodeTerminal nodeTerminal = (BookNodeTerminal) node;
			if(nodeTerminal.getBookNodeStatus() == BookNodeStatus.FAILURE)
				setDefaultColor(Color.DARKRED);
			else
				setDefaultColor(Color.DARKGREEN);
		} else if(node instanceof BookNodeWithChoices){
				setDefaultColor(Color.CHOCOLATE);
		} else if(node instanceof BookNodeWithRandomChoices){
				setDefaultColor(Color.DARKTURQUOISE);
		}
	}
	
}
