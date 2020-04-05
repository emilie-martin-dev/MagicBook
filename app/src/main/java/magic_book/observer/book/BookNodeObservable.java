package magic_book.observer.book;

import magic_book.core.graph.node.AbstractBookNode;
import magic_book.observer.Observable;

/**
 * Permet de notifier d'un changement sur les noeuds du livre
 */
public class BookNodeObservable extends Observable<BookNodeObserver> {

	/**
	 * Notifie l'ajout d'un noeud
	 * @param node Le noeud ajouté
	 */
	public void notifyNodeAdded(AbstractBookNode node) {
		for (BookNodeObserver nodeObserver : getObservers()) {
			nodeObserver.nodeAdded(node);
		}
	}

	/**
	 * Notifie la suppression d'un noeud
	 * @param node Le noeud supprimé
	 */
	public void notifyNodeDeleted(AbstractBookNode node) {
		for (BookNodeObserver nodeObserver : getObservers()) {
			nodeObserver.nodeDeleted(node);
		}
	}

	/**
	 * Notifie la mise a jour d'un noeud
	 * @param oldNode L'ancien noeud
	 * @param newNode Le nouveau noeud
	 */	
	public void notifyNodeEdited(AbstractBookNode oldNode, AbstractBookNode newNode) {
		for (BookNodeObserver nodeObserver : getObservers()) {
			nodeObserver.nodeEdited(oldNode, newNode);
		}
	}

}
