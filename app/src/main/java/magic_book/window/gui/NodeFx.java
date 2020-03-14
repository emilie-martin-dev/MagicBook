package magic_book.window.gui;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

import magic_book.core.node.BookNode;
import magic_book.observer.RectangleFxObservable;
import magic_book.observer.RectangleFxObserver;

public class NodeFx extends RectangleFx {
	
	private BookNode node;
	

	public NodeFx(BookNode node) {
		super();
		this.node = node;
	}

	public BookNode getNode() {
		return node;
	}

	public void setNode(BookNode node) {
		this.node = node;
	}
	
}
