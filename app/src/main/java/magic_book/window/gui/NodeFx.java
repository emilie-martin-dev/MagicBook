package magic_book.window.gui;

import javafx.scene.paint.Color;

import magic_book.core.node.BookNode;

public class NodeFx extends RectangleFx {
	
	private BookNode node;
	

	public NodeFx(BookNode node) {
		super(Color.GREEN);
		
		this.node = node;
	}

	public BookNode getNode() {
		return node;
	}

	public void setNode(BookNode node) {
		this.node = node;
	}
	
}
