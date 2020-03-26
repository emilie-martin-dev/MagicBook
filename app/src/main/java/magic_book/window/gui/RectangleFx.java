package magic_book.window.gui;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import magic_book.observer.fx.RectangleFxObservable;
import magic_book.observer.fx.RectangleFxObserver;

public class RectangleFx extends Rectangle {

	private RectangleFxObservable nodeFxObservable;

	public RectangleFx(Color color) {	
		nodeFxObservable = new RectangleFxObservable();
		
		this.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
			nodeFxObservable.notifyOnNodeFXClicked(RectangleFx.this, event);
		});
		
		this.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				RectangleFx.this.setX(event.getX()-RectangleFx.this.getWidth()/2);
				RectangleFx.this.setY(event.getY()-RectangleFx.this.getHeight()/2);
				
				if (RectangleFx.this.getX() < 0){
					RectangleFx.this.setX(0);
				}
				
				if(RectangleFx.this.getY() < 0){
					RectangleFx.this.setY(0);
				}
				
				event.consume();
			}
		});
		
		this.setWidth(50);
		this.setHeight(50);
		this.setFill(color);
	}
	
	public void addNodeFxObserver(RectangleFxObserver observer) {
		nodeFxObservable.addObserver(observer);
	}
}