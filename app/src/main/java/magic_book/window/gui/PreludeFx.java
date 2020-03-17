package magic_book.window.gui;

import javafx.scene.paint.Color;

public class PreludeFx extends RectangleFx {
	
	private NodeFx firstNode;
	private String text;
	
	public PreludeFx(String texte) {
		super(Color.RED);
		
		this.text = texte;
	}
	
	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public NodeFx getFirstNode() {
		return firstNode;
	}

	public void setFirstNode(NodeFx firstNode) {
		this.firstNode = firstNode;
	}
	
}