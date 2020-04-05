package magic_book.window.gui;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import magic_book.observer.fx.RectangleFxObservable;
import magic_book.observer.fx.RectangleFxObserver;
import magic_book.window.UiConsts;

public class RectangleFx extends Rectangle {

	public static final int WIDTH = 50;
	
	private RectangleFxObservable nodeFxObservable;
	
	private SimpleFloatProperty realX;
	private SimpleFloatProperty realY;
	
	private FloatProperty zoom;
	
	private Color defaultColor;

	public RectangleFx(Color color, FloatProperty zoom) {
		this.defaultColor = color;
		nodeFxObservable = new RectangleFxObservable();
		this.defaultColor = color;
		
		this.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
			nodeFxObservable.notifyOnNodeFXClicked(RectangleFx.this, event);
		});
		
		this.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				RectangleFx.this.setOpacity(0.5f);
			}
		});
		
		this.setOnMouseExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				RectangleFx.this.setOpacity(100f);
			}
		});
		
		this.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				RectangleFx.this.setRealX((event.getX() - RectangleFx.this.getWidth() / 2) / zoom.get());
				RectangleFx.this.setRealY((event.getY() - RectangleFx.this.getHeight() / 2) / zoom.get());
				
				if (RectangleFx.this.getRealX() < 0){
					RectangleFx.this.setRealX(0);
				}
				
				if(RectangleFx.this.getRealY() < 0){
					RectangleFx.this.setRealY(0);
				}
				
				event.consume();
			}
		});
		
		this.setWidth(UiConsts.RECTANGLE_FX_SIZE);
		this.setHeight(UiConsts.RECTANGLE_FX_SIZE);
		
		realX = new SimpleFloatProperty();
		realY = new SimpleFloatProperty();
		this.zoom = zoom;
		
		this.widthProperty().bind(zoom.multiply(UiConsts.RECTANGLE_FX_SIZE));
		this.heightProperty().bind(zoom.multiply(UiConsts.RECTANGLE_FX_SIZE));
		
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
	
	public Color getDefaultColor() {
		return defaultColor;
	}

	public void setDefaultColor(Color defaultColor) {
		this.defaultColor = defaultColor;
		this.setFill(defaultColor);
	}

}