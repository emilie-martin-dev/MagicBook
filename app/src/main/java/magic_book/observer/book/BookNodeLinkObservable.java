package magic_book.observer.book;

import magic_book.core.graph.node.AbstractBookNodeWithChoices;
import magic_book.core.graph.node_link.BookNodeLink;
import magic_book.observer.Observable;

public class BookNodeLinkObservable extends Observable<BookNodeLinkObserver> {

	public void notifyNodeLinkAdded(BookNodeLink nodeLink, AbstractBookNodeWithChoices node) {
		for (BookNodeLinkObserver nodeLinkObserver : getObservers()) {
			nodeLinkObserver.nodeLinkAdded(nodeLink, node);
		}
	}

	public void notifyNodeLinkDeleted(BookNodeLink nodeLink) {
		for (BookNodeLinkObserver nodeLinkObserver : getObservers()) {
			nodeLinkObserver.nodeLinkDeleted(nodeLink);
		}
	}

	public void notifyNodeLinkEdited(BookNodeLink oldNodeLink, BookNodeLink newNodeLink) {
		for (BookNodeLinkObserver nodeLinkObserver : getObservers()) {
			nodeLinkObserver.nodeLinkEdited(oldNodeLink, newNodeLink);
		}
	}

}
