package magic_book.observer;

import magic_book.core.node.BookNode;

public class NodeObservable extends Observable<NodeObserver> {

	public void notifyNodeAdded(BookNode node) {
		for (NodeObserver nodeObserver : listObservers) {
			nodeObserver.nodeAdded(node);
		}
	}

	public void notifyNodeDeleted(BookNode node) {
		for (NodeObserver nodeObserver : listObservers) {
			nodeObserver.nodeDeleted(node);
		}
	}

	public void notifyNodeEdited(BookNode node) {
		for (NodeObserver nodeObserver : listObservers) {
			nodeObserver.nodeEdited(node);
		}
	}

}
