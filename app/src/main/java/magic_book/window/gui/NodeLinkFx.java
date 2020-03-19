package magic_book.window.gui;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import magic_book.core.graph.node_link.BookNodeLink;
import magic_book.observer.NodeLinkFxObservable;
import magic_book.observer.NodeLinkFxObserver;

public class NodeLinkFx extends Line {
	
	private BookNodeLink nodeLink;
	private NodeFx startNode;
	private NodeFx endNode;
	
	private NodeLinkFxObservable nodeLinkFxObservable;

	public NodeLinkFx(BookNodeLink nodeLink, NodeFx startNode, NodeFx endNode) {
		this.nodeLink = nodeLink;
		this.startNode = startNode;
		this.endNode = endNode;
		
		this.setStrokeWidth(3);
		this.setStroke(Color.BLACK);
		
		nodeLinkFxObservable = new NodeLinkFxObservable();
		
		this.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
			nodeLinkFxObservable.notifyOnNodeLinkFXClicked(NodeLinkFx.this, event);
		});				
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
