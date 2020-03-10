package magic_book.observer;

import javafx.scene.input.MouseEvent;
import magic_book.window.gui.RectangleFx;

public interface RectangleFxObserver {

	public void onRectangleFXClicked(RectangleFx rectangleFx, MouseEvent event);
	
}
