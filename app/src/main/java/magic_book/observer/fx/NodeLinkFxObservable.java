package magic_book.observer.fx;

import javafx.scene.input.MouseEvent;
import magic_book.observer.Observable;
import magic_book.window.gui.NodeLinkFx;

/**
 * Permet de notifier d'un clic sur un NodeLinkFx
 */
public class NodeLinkFxObservable extends Observable<NodeLinkFxObserver> {
	
	/**
	 * Notifie d'un clic sur un NodeLinkFx
	 * @param nodeLinkFx Le NodeLinkFx cliqué
	 * @param event L'évènement du clic
	 */
	public void notifyOnNodeLinkFXClicked(NodeLinkFx nodeLinkFx, MouseEvent event) {
		for (NodeLinkFxObserver nodeObserver : getObservers()) {
			nodeObserver.onNodeLinkFXClicked(nodeLinkFx, event);
		}
	}

}
