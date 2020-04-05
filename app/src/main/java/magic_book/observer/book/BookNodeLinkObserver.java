package magic_book.observer.book;

import magic_book.core.graph.node.AbstractBookNodeWithChoices;
import magic_book.core.graph.node_link.BookNodeLink;

/**
 * Observer pour réagir aux changements sur les liens
 */
public interface BookNodeLinkObserver {

	/**
	 * Un lien a été ajouté
	 * @param nodeLink Le lien ajouté
	 * @param node Le noeud sur lequel on a ajouté le lien
	 */
	public void nodeLinkAdded(BookNodeLink nodeLink, AbstractBookNodeWithChoices node);

	/**
	 * Un lien a été mis a jour
	 * @param oldNodeLink L'ancien lien
	 * @param newNodeLink Le nouveau lien
	 */
	public void nodeLinkEdited(BookNodeLink oldNodeLink, BookNodeLink newNodeLink);

	/**
	 * Un lien a été supprimé
	 * @param nodeLink Le lien supprimé
	 */
	public void nodeLinkDeleted(BookNodeLink nodeLink);
	
}
