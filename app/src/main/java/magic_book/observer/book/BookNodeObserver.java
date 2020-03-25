package magic_book.observer.book;

import magic_book.core.graph.node.AbstractBookNode;

public interface BookNodeObserver {

	public void nodeAdded(AbstractBookNode node);

	public void nodeEdited(AbstractBookNode oldNode, AbstractBookNode newNode);

	public void nodeDeleted(AbstractBookNode node);
	
}
