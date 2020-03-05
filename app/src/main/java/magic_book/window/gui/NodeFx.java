package magic_book.window.gui;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import magic_book.core.node.BookNode;
import magic_book.observer.NodeFxObservable;
import magic_book.observer.NodeFxObserver;
import magic_book.observer.NodeObserver;
import magic_book.window.dialog.NodeDialog;

public class NodeFx extends Rectangle {
	
	private BookNode node;
	
	private EventHandler<MouseEvent> addNodeEvent;
	private EventHandler<MouseEvent> modEventHandler;
	
	private NodeFxObservable nodeFxObservable;

	public NodeFx(BookNode node) {
		this.node = node;
		nodeFxObservable = new NodeFxObservable();
		
		this.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				nodeFxObservable.notifyOnNodeFXClicked(NodeFx.this, event);
			}
		});
		
	/*	modEventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
					if(event.getClickCount() == 2){
						System.out.println("Double clicked");
						NodeDialog dialog = new NodeDialog(NodeFx.this.node);
						if(dialog.getNode() != null) {
							NodeFx.this.node = dialog.getNode();
						}
					}
				event.consume();
			}
		};*/
				
	}

	public BookNode getNode() {
		return node;
	}

	public void setNode(BookNode node) {
		this.node = node;
	}
	
	public void addNodeFxObserver(NodeFxObserver observer) {
		nodeFxObservable.addObserver(observer);
	}
	
}
