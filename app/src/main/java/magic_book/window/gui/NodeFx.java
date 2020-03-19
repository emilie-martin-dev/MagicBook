package magic_book.window.gui;

import javafx.scene.paint.Color;

import magic_book.core.graph.node.AbstractBookNode;

public class NodeFx extends RectangleFx {
	
	private AbstractBookNode node;
	

	public NodeFx(AbstractBookNode node) {
		super(Color.GREEN);
		
		this.node = node;
	}

	public AbstractBookNode getNode() {
		return node;
	}

	public void setNode(AbstractBookNode node) {
		this.node = node;
	}
	
}
