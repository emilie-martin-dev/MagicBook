package magic_book.window.gui;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import magic_book.core.node.BookNode;
import magic_book.observer.NodeFxObservable;
import magic_book.observer.NodeFxObserver;

public class NodeFx extends Rectangle {
	
	private BookNode node;
	
	private NodeFxObservable nodeFxObservable;

	public NodeFx(BookNode node) {
		this.node = node;
		nodeFxObservable = new NodeFxObservable();
		
		this.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
			nodeFxObservable.notifyOnNodeFXClicked(NodeFx.this, event);
		});
		
		this.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent select) {
				if (select.getX() > 0 && select.getY() > 0){
					NodeFx.this.setX(select.getX()-NodeFx.this.getWidth()/2);
					NodeFx.this.setY(select.getY()-NodeFx.this.getHeight()/2);
				} else if (select.getX() <= 0){ 
					NodeFx.this.setX(0.1);
				} else if (select.getY() <= 0){ 
					NodeFx.this.setY(0.1);
				}
			}
		});
		
		this.setWidth(50);
		this.setHeight(50);
		this.setFill(Color.GREEN);
	}
	
	public void addNodeFxObserver(NodeFxObserver observer) {
		nodeFxObservable.addObserver(observer);
	}

	public BookNode getNode() {
		return node;
	}

	public void setNode(BookNode node) {
		this.node = node;
	}
	
}
