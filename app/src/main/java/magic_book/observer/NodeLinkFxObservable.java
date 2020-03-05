package magic_book.observer;

import javafx.scene.input.MouseEvent;
import magic_book.window.gui.NodeLinkFx;

public class NodeLinkFxObservable extends Observable<NodeLinkFxObserver> {

	public void notifyOnNodeLinkFXClicked(NodeLinkFx nodeLinkFx, MouseEvent event) {
		for (NodeLinkFxObserver nodeObserver : listObservers) {
			nodeObserver.onNodeLinkFXClicked(nodeLinkFx, event);
		}
	}

}
