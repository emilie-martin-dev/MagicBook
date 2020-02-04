package magic_book.observer;

import magic_book.core.node.Node;

public interface NodeObserver {

	public void nodeAdded(Node node);

	public void nodeDeleted(Node node);

	public void nodeEdited(Node node);

}
