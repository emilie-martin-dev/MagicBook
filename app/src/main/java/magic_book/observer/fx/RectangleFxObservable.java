package magic_book.observer.fx;

import javafx.scene.input.MouseEvent;
import magic_book.observer.Observable;
import magic_book.window.gui.RectangleFx;

/**
 * Permet de notifier d'un clic sur un RectangleFx
 */
public class RectangleFxObservable extends Observable<RectangleFxObserver> {

	/**
	 * Notifie d'un clic sur un RectangleFx
	 * @param rectangleFx Le RectangleFx cliqué
	 * @param event L'évènement du clic
	 */
	public void notifyOnNodeFXClicked(RectangleFx rectangleFx, MouseEvent event) {
		for (RectangleFxObserver nodeObserver : getObservers()) {
			nodeObserver.onRectangleFXClicked(rectangleFx, event);
		}
	}

}
