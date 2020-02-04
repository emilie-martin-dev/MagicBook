package magic_book.observer;

import magic_book.core.node.Node;

public class NodeObservable extends Observable<NodeObserver> {

	public void notifyNodeAdded(Node node) {
		for (NodeObserver nodeObserver : listObservers) {
			nodeObserver.nodeAdded(node);
		}
	}

	public void notifyNodeDeleted(Node node) {
		for (NodeObserver nodeObserver : listObservers) {
			nodeObserver.nodeDeleted(node);
		}
	}

	public void notifyNodeEdited(Node node) {
		for (NodeObserver nodeObserver : listObservers) {
			nodeObserver.nodeEdited(node);
		}
	}

}
