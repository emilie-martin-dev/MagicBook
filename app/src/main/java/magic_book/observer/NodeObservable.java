package magic_book.observer;

import magic_book.core.node.AbstractBookNode;

public class NodeObservable extends Observable<NodeObserver> {

	public void notifyNodeAdded(AbstractBookNode node) {
		for (NodeObserver nodeObserver : listObservers) {
			nodeObserver.nodeAdded(node);
		}
	}

	public void notifyNodeDeleted(AbstractBookNode node) {
		for (NodeObserver nodeObserver : listObservers) {
			nodeObserver.nodeDeleted(node);
		}
	}

	public void notifyNodeEdited(AbstractBookNode node) {
		for (NodeObserver nodeObserver : listObservers) {
			nodeObserver.nodeEdited(node);
		}
	}

}
