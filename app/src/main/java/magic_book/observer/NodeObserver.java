package magic_book.observer;

import magic_book.core.node.BookNode;

public interface NodeObserver {

	public void nodeAdded(BookNode node);

	public void nodeDeleted(BookNode node);

	public void nodeEdited(BookNode node);

}
