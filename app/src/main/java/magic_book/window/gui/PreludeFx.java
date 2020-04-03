package magic_book.window.gui;

import javafx.beans.property.FloatProperty;
import javafx.scene.paint.Color;

public class PreludeFx extends RectangleFx {
	
	private NodeFx firstNode;
	
	public PreludeFx(String texte, FloatProperty zoom) {
		super(Color.DEEPPINK, zoom);
	}

	public NodeFx getFirstNode() {
		return firstNode;
	}

	public void setFirstNode(NodeFx firstNode) {
		this.firstNode = firstNode;
	}
	
}