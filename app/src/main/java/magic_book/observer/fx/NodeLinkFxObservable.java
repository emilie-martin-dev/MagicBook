package magic_book.observer.fx;

import javafx.scene.input.MouseEvent;
import magic_book.observer.Observable;
import magic_book.window.gui.NodeLinkFx;

public class NodeLinkFxObservable extends Observable<NodeLinkFxObserver> {

	public void notifyOnNodeLinkFXClicked(NodeLinkFx nodeLinkFx, MouseEvent event) {
		for (NodeLinkFxObserver nodeObserver : getObservers()) {
			nodeObserver.onNodeLinkFXClicked(nodeLinkFx, event);
		}
	}

}
