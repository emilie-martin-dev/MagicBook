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

/**
 * Rectangle permettant de voir visuellement le noeud
 */
public class RectangleFx extends Rectangle {

	/**
	 * Taille final du rectangle
	 */
	public static final int WIDTH = 50;
	
	/**
	 * Observable sur le rectangle
	 */
	private RectangleFxObservable nodeFxObservable;
	
	/**
	 * Position x du coin gauche du rectangle pour le zoom
	 */
	private SimpleFloatProperty realX;
	/**
	 * Position y coin gauche du rectangle pour le zoom
	 */
	private SimpleFloatProperty realY;
	
	/**
	 * Zoom
	 */
	private FloatProperty zoom;
	
	/**
	 * Couleur par défaut du rectangle
	 */
	private Color defaultColor;

	/**
	 * Initialisation du rectangle
	 * @param color Couleur du rectangle
	 * @param zoom Valeur du zoom
	 */
	public RectangleFx(Color color, FloatProperty zoom) {
		this.defaultColor = color;
		nodeFxObservable = new RectangleFxObservable();
		this.defaultColor = color;
		
		//Si le rectangle est sélectionné, on envoie l'évènement et le rectangle sélectionné dans le centre de la fenêtre du MainWindows
		this.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
			nodeFxObservable.notifyOnNodeFXClicked(RectangleFx.this, event);
		});
		
		//Si la souris passe sur le rectangle, on modifie l'opacité du rectangle
		this.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				RectangleFx.this.setOpacity(0.5f);
			}
		});
		
		//Si la souris sort du rectangle, l'opacité revient à la normal
		this.setOnMouseExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				RectangleFx.this.setOpacity(100f);
			}
		});
		
		//Permet de modifier la position du rectangle si le rectangle perçois un clic maintenu de la souris
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
	
	/**
	 * Ajoute un observeur sur le rectangle
	 * @param observer Observeur
	 */
	public void addNodeFxObserver(RectangleFxObserver observer) {
		nodeFxObservable.addObserver(observer);
	}

	/**
	 * Donne la position du coin gauche du rectangle pour le zoom
	 * @return position x
	 */
	public double getRealX() {
		return realX.get();
	}

	/**
	 * Modifie la position x du coin gauche du rectangle pour le zoom
	 * @param realX position du x
	 */
	public void setRealX(double realX) {
		this.realX.set((float) realX);
	}

	/**
	 * Donne la position du coin gauche du rectangle pour le zoom
	 * @return position y
	 */
	public double getRealY() {
		return realY.get();
	}

	/**
	 * Modifie la position y du coin gauche du rectangle pour le zoom
	 * @param realX position du y
	 */
	public void setRealY(double realY) {
		this.realY.set((float) realY);
	}
	
	/**
	 * Donne la couleur
	 * @return couleur
	 */
	public Color getDefaultColor() {
		return defaultColor;
	}

	/**
	 * Modifie la couleur par défault du rectangle
	 * @param defaultColor Couleur voulue
	 */
	public void setDefaultColor(Color defaultColor) {
		this.defaultColor = defaultColor;
		this.setFill(defaultColor);
	}

}