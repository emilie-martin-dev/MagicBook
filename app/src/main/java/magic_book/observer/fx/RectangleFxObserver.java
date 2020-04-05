package magic_book.observer.fx;

import javafx.scene.input.MouseEvent;
import magic_book.window.gui.RectangleFx;

/**
 * Observer pour réagir aux clics sur un RectangleFx
 */
public interface RectangleFxObserver {

	/**
	 * Le RectangleFx a été cliqué
	 * @param rectangleFx Le RectangleFx cliqué
	 * @param event L'évènement du clic
	 */
	public void onRectangleFXClicked(RectangleFx rectangleFx, MouseEvent event);
	
}
