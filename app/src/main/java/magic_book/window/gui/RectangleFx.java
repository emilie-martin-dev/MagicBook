package magic_book.window.gui;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import magic_book.observer.fx.RectangleFxObservable;
import magic_book.observer.fx.RectangleFxObserver;

public class RectangleFx extends Rectangle {

	private RectangleFxObservable nodeFxObservable;
	
	private SimpleFloatProperty realX;
	private SimpleFloatProperty realY;
	
	private FloatProperty zoom;

	public RectangleFx(Color color, FloatProperty zoom) {	
		nodeFxObservable = new RectangleFxObservable();
		
		this.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
			nodeFxObservable.notifyOnNodeFXClicked(RectangleFx.this, event);
		});
		
		this.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				RectangleFx.this.setRealX(event.getX() / zoom.get() - RectangleFx.this.getWidth() / 2);
				RectangleFx.this.setRealY(event.getY() / zoom.get() - RectangleFx.this.getHeight() / 2);
				
				if (RectangleFx.this.getRealX() < 0){
					RectangleFx.this.setRealX(0);
				}
				
				if(RectangleFx.this.getRealY() < 0){
					RectangleFx.this.setRealY(0);
				}
				
				event.consume();
			}
		});
		
		realX = new SimpleFloatProperty();
		realY = new SimpleFloatProperty();
		this.zoom = zoom;
		
		this.widthProperty().bind(zoom.multiply(50));
		this.heightProperty().bind(zoom.multiply(50));
		
		this.xProperty().bind(zoom.multiply(realX));
		this.yProperty().bind(zoom.multiply(realY));
		
		this.setFill(color);
	}
	
	public void addNodeFxObserver(RectangleFxObserver observer) {
		nodeFxObservable.addObserver(observer);
	}

	public double getRealX() {
		return realX.get();
	}

	public void setRealX(double realX) {
		this.realX.set((float) realX);
	}

	public double getRealY() {
		return realY.get();
	}

	public void setRealY(double realY) {
		this.realY.set((float) realY);
	}
	
}