package magic_book.observer.book;

import magic_book.core.graph.node_link.BookNodeLink;

public interface BookNodeLinkObserver {

	public void nodeLinkAdded(BookNodeLink nodeLink);

	public void nodeLinkEdited(BookNodeLink oldNodeLink, BookNodeLink newNodeLink);

	public void nodeLinkDeleted(BookNodeLink nodeLink);
	
}
