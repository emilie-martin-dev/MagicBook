package magic_book.observer.book;

import magic_book.core.graph.node.AbstractBookNode;
import magic_book.observer.Observable;

public class BookNodeObservable extends Observable<BookNodeObserver> {

	public void notifyNodeAdded(AbstractBookNode node) {
		for (BookNodeObserver nodeObserver : getObservers()) {
			nodeObserver.nodeAdded(node);
		}
	}

	public void notifyNodeDeleted(AbstractBookNode node) {
		for (BookNodeObserver nodeObserver : getObservers()) {
			nodeObserver.nodeDeleted(node);
		}
	}

	public void notifyNodeEdited(AbstractBookNode oldNode, AbstractBookNode newNode) {
		for (BookNodeObserver nodeObserver : getObservers()) {
			nodeObserver.nodeEdited(oldNode, newNode);
		}
	}

}
