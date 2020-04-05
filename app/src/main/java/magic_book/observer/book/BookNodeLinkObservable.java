package magic_book.observer.book;

import magic_book.core.graph.node.AbstractBookNodeWithChoices;
import magic_book.core.graph.node_link.BookNodeLink;
import magic_book.observer.Observable;

/**
 * Permet de notifier d'un changement sur les liens du livre
 */
public class BookNodeLinkObservable extends Observable<BookNodeLinkObserver> {

	/**
	 * Notifie l'ajout d'un lien
	 * @param nodeLink Le lien ajouté
	 * @param node Le noeud sur lequel on a ajouté le lien
	 */
	public void notifyNodeLinkAdded(BookNodeLink nodeLink, AbstractBookNodeWithChoices node) {
		for (BookNodeLinkObserver nodeLinkObserver : getObservers()) {
			nodeLinkObserver.nodeLinkAdded(nodeLink, node);
		}
	}

	/**
	 * Notifie la suppression d'un lien
	 * @param nodeLink Le lien supprimé
	 */
	public void notifyNodeLinkDeleted(BookNodeLink nodeLink) {
		for (BookNodeLinkObserver nodeLinkObserver : getObservers()) {
			nodeLinkObserver.nodeLinkDeleted(nodeLink);
		}
	}

	/**
	 * Notifie la mise à jour d'un lien
	 * @param oldNodeLink L'ancien lien
	 * @param newNodeLink Le nouveau lien
	 */
	public void notifyNodeLinkEdited(BookNodeLink oldNodeLink, BookNodeLink newNodeLink) {
		for (BookNodeLinkObserver nodeLinkObserver : getObservers()) {
			nodeLinkObserver.nodeLinkEdited(oldNodeLink, newNodeLink);
		}
	}

}
