package magic_book.window.gui;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

import magic_book.core.node.BookNode;
import magic_book.observer.RectangleFxObservable;
import magic_book.observer.RectangleFxObserver;

public class PreludeFx extends RectangleFx {
	
	private String texte;
	
	public PreludeFx(String texte) {
		super();
		this.texte = texte;
	}
	
		public String getText() {
		return this.texte;
	}

	public void setText(String text) {
		this.texte = texte;
	}
	
}