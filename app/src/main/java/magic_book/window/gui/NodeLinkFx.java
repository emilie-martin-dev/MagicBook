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

public class NodeLinkFx extends Line {
	
	private BookNodeLink nodeLink;
	private NodeFx startNode;
	private NodeFx endNode;
	
	private Circle endCircle;
	
	private NodeLinkFxObservable nodeLinkFxObservable;

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
		
		this.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
			nodeLinkFxObservable.notifyOnNodeLinkFXClicked(NodeLinkFx.this, event);
		});	
		
		endCircle.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
			nodeLinkFxObservable.notifyOnNodeLinkFXClicked(NodeLinkFx.this, event);
		});	
	}
	
	public void registerComponent(Pane rootPane) {
		rootPane.getChildren().add(this);
		rootPane.getChildren().add(endCircle);
	}
	
	public void unregisterComponent(Pane rootPane) {
		rootPane.getChildren().remove(this);
		rootPane.getChildren().remove(endCircle);
	}
	
	public void addNodeLinkFxObserver(NodeLinkFxObserver observer) {
		nodeLinkFxObservable.addObserver(observer);
	}

	public BookNodeLink getNodeLink() {
		return nodeLink;
	}

	public void setNodeLink(BookNodeLink nodeLink) {
		this.nodeLink = nodeLink;
	}

	public NodeFx getStart() {
		return startNode;
	}

	public void setStart(NodeFx startNode) {
		this.startNode = startNode;
	}

	public NodeFx getEnd() {
		return endNode;
	}

	public void setEnd(NodeFx endNode) {
		this.endNode = endNode;
	}
	
}
