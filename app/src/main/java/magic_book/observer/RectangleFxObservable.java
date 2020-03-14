package magic_book.observer;

import javafx.scene.input.MouseEvent;
import magic_book.window.gui.RectangleFx;

public class RectangleFxObservable extends Observable<RectangleFxObserver> {

	public void notifyOnNodeFXClicked(RectangleFx rectangleFx, MouseEvent event) {
		for (RectangleFxObserver nodeObserver : listObservers) {
			nodeObserver.onRectangleFXClicked(rectangleFx, event);
		}
	}

}
