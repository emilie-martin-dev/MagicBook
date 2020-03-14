package magic_book.observer;

import magic_book.core.node.AbstractBookNode;

public interface NodeObserver {

	public void nodeAdded(AbstractBookNode node);

	public void nodeDeleted(AbstractBookNode node);

	public void nodeEdited(AbstractBookNode node);

}
