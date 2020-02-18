package magic_book.window.gui;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import magic_book.core.node.BookNode;
import magic_book.window.dialog.NodeDialog;

public class NodeFx extends Rectangle {
	
	private BookNode node;

	public NodeFx(BookNode node) {
		this.node = node;
		
		this.addEventHandler(MouseEvent.MOUSE_PRESSED, (new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				NodeDialog dialog = new NodeDialog(NodeFx.this.node);
				if(dialog.getNode() != null) {
					NodeFx.this.node = dialog.getNode();
				}
				
				event.consume();
			}
		}));
	}

	public BookNode getNode() {
		return node;
	}

	public void setNode(BookNode node) {
		this.node = node;
	}	
	
}
