package magic_book.observer.fx;

import javafx.scene.input.MouseEvent;
import magic_book.observer.Observable;
import magic_book.window.gui.RectangleFx;

public class RectangleFxObservable extends Observable<RectangleFxObserver> {

	public void notifyOnNodeFXClicked(RectangleFx rectangleFx, MouseEvent event) {
		for (RectangleFxObserver nodeObserver : getObservers()) {
			nodeObserver.onRectangleFXClicked(rectangleFx, event);
		}
	}

}
