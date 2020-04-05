package magic_book.observer.book;

import magic_book.core.graph.node.AbstractBookNode;

/**
 * Observer pour réagir aux changements sur les noeuds
 */
public interface BookNodeObserver {

	/**
	 * Un noeud a été ajouté
	 * @param node Le noeud ajouté
	 */
	public void nodeAdded(AbstractBookNode node);

	/**
	 * Un noeud a été mis a jour
	 * @param oldNode L'ancien noeud
	 * @param newNode Le nouveau noeud
	 */
	public void nodeEdited(AbstractBookNode oldNode, AbstractBookNode newNode);

	/**
	 * Un noeud a été supprimé
	 * @param node Le noeud supprimé
	 */
	public void nodeDeleted(AbstractBookNode node);
	
}
