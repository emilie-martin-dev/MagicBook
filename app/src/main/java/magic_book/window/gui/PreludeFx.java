package magic_book.window.gui;

import javafx.scene.paint.Color;

public class PreludeFx extends RectangleFx {
	
	private String texte;
	
	public PreludeFx(String texte) {
		super();
		
		this.texte = texte;
		
		this.setFill(Color.RED);
	}
	
	public String getText() {
		return this.texte;
	}

	public void setText(String text) {
		this.texte = text;
	}
	
}