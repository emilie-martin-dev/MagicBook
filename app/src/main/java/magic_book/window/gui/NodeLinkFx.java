package magic_book.window.gui;

import javafx.beans.property.FloatProperty;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import magic_book.core.graph.node_link.BookNodeLink;
import magic_book.observer.fx.NodeLinkFxObservable;
import magic_book.observer.fx.NodeLinkFxObserver;

/**
 * Représente graphiquement un lien entre deux noeud
 */
public class NodeLinkFx extends Line {
	
	/**
	 * Lien rattaché
	 */
	private BookNodeLink nodeLink;
	/**
	 * Noeud de départ
	 */
	private NodeFx startNode;
	/**
	 * Noeud de destination
	 */
	private NodeFx endNode;
	
	/**
	 * Cercle pour le noeud graphique de destination
	 */
	private Circle endCircle;
	
	/**
	 * Observable pour notifier d'un évènement
	 */
	private NodeLinkFxObservable nodeLinkFxObservable;

	/**
	 * Création du lien
	 * @param nodeLink Lien rattaché
	 * @param startNode Noeud de départ
	 * @param endNode Noeud d'arrivé
	 * @param zoom Valeur du zoom
	 */
	public NodeLinkFx(BookNodeLink nodeLink, NodeFx startNode, NodeFx endNode, FloatProperty zoom) {
		this.nodeLink = nodeLink;
		this.startNode = startNode;
		this.endNode = endNode;
		
		this.strokeWidthProperty().bind(zoom.multiply(3));
		this.setStroke(Color.BLACK);
		
		endCircle = new Circle();
		endCircle.radiusProperty().bind(zoom.multiply(4));
		endCircle.setStroke(Color.BLACK);
		endCircle.centerXProperty().bind(endXProperty());
		endCircle.centerYProperty().bind(endYProperty());
		
		nodeLinkFxObservable = new NodeLinkFxObservable();
		
		//Si le lien est cliqué
		this.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
			nodeLinkFxObservable.notifyOnNodeLinkFXClicked(NodeLinkFx.this, event);
		});	
		
		//Si le cercle est cliqué
		endCircle.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
			nodeLinkFxObservable.notifyOnNodeLinkFXClicked(NodeLinkFx.this, event);
		});	
	}
	
	/**
	 * Ajoute le lien et le cercle du noeud graphique de fin au Pane
	 * @param rootPane Pane auquel on doit ajouter le lien
	 */
	public void registerComponent(Pane rootPane) {
		rootPane.getChildren().add(this);
		rootPane.getChildren().add(endCircle);
	}
	
	/**
	 * Enlève le lien et le cercle du noeud graphique de fin au Pane
	 * @param rootPane ane auquel on doit ajouter le lien
	 */
	public void unregisterComponent(Pane rootPane) {
		rootPane.getChildren().remove(this);
		rootPane.getChildren().remove(endCircle);
	}
	
	/**
	 * Ajoute un observeur sur le lien
	 * @param observer Observeur sur ce lien
	 */
	public void addNodeLinkFxObserver(NodeLinkFxObserver observer) {
		nodeLinkFxObservable.addObserver(observer);
	}

	/**
	 * Donne le lien rattaché
	 * @return Lien attaché
	 */
	public BookNodeLink getNodeLink() {
		return nodeLink;
	}

	/**
	 * Modifie le lien rattaché
	 * @param nodeLink Nouveau lien à rattacher
	 */
	public void setNodeLink(BookNodeLink nodeLink) {
		this.nodeLink = nodeLink;
	}

	/**
	 * Donne le noeud graphique de départ
	 * @return Noeud de départ
	 */
	public NodeFx getStart() {
		return startNode;
	}

	/**
	 * Modifie le noeud graphique de départ
	 * @param startNode Nouveau noeud graphique de départ
	 */
	public void setStart(NodeFx startNode) {
		this.startNode = startNode;
	}

	/**
	 * Donne le noeud graphique de fin
	 * @return Noeud de fin
	 */
	public NodeFx getEnd() {
		return endNode;
	}

	/**
	 * Modifie le noeud graphique de destination
	 * @param endNode Nouveau noeud graphique de destination
	 */
	public void setEnd(NodeFx endNode) {
		this.endNode = endNode;
	}
	
}
