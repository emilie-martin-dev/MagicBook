package magic_book.window.gui;

import javafx.beans.property.FloatProperty;
import javafx.scene.paint.Color;

/**
 * Représentation graphique du prélude
 */
public class PreludeFx extends RectangleFx {
	
	/**
	 * Premier noeud du livre
	 */
	private NodeFx firstNode;
	
	/**
	 * Constructeur 
	 * @param zoom Valeur du zoom
	 */
	public PreludeFx(FloatProperty zoom) {
		super(Color.DEEPPINK, zoom);
	}

	/**
	 * Donne le noeud de départ
	 * @return Noeud de départ
	 */
	public NodeFx getFirstNode() {
		return firstNode;
	}

	/**
	 * Modifie le noeud de départ
	 * @param firstNode Nouveau noeud de départ
	 */
	public void setFirstNode(NodeFx firstNode) {
		this.firstNode = firstNode;
	}
	
}