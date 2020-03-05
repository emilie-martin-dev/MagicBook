package magic_book.observer;

import javafx.scene.input.MouseEvent;
import magic_book.window.gui.NodeFx;

public class NodeFxObservable extends Observable<NodeFxObserver> {

	public void notifyOnNodeFXClicked(NodeFx nodeFx, MouseEvent event) {
		for (NodeFxObserver nodeObserver : listObservers) {
			nodeObserver.onNodeFXClicked(nodeFx, event);
		}
	}

}
