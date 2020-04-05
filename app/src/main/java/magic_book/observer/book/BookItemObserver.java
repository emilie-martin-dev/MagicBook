package magic_book.observer.book;

import magic_book.core.item.BookItem;

/**
 * Observer pour réagir aux changements sur les items
 */
public interface BookItemObserver {

	/**
	 * Un item a été ajouté
	 * @param item L'item ajouté
	 */
	public void itemAdded(BookItem item);

	/**
	 * Un item a été mis à jour
	 * @param oldItem L'ancien item
	 * @param newItem Le nouvel item
	 */
	public void itemEdited(BookItem oldItem, BookItem newItem);

	/**
	 * Un item a été supprimé
	 * @param item L'item supprimé
	 */
	public void itemDeleted(BookItem item);
	
}
