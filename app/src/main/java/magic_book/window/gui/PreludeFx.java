package magic_book.window.gui;

import javafx.beans.property.FloatProperty;
import javafx.scene.paint.Color;

/**
 * Contient le prélude et construit le rectangle
 */
public class PreludeFx extends RectangleFx {
	
	/**
	 * Prélude
	 */
	private NodeFx firstNode;
	/**
	 * Texte du prélude
	 */
	private String text;
	
	/**
	 * Initialisation du rectangle et du texte du prélude
	 * @param texte Texte du prélude
	 * @param zoom Valeur du zoom
	 */
	public PreludeFx(String texte, FloatProperty zoom) {
		super(Color.DEEPPINK, zoom);
		
		this.text = texte;
	}
	
	/**
	 * Donne le texte du prélude
	 * @return Texte
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * Modifie le texte du prélude
	 * @param text Texte
	 */
	public void setText(String text) {
		this.text = text;
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