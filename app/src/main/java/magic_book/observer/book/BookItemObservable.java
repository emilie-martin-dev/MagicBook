package magic_book.observer.book;

import magic_book.core.item.BookItem;
import magic_book.observer.Observable;

/**
 * Permet de notifier d'un changement sur les items du livre
 */
public class BookItemObservable extends Observable<BookItemObserver> {

	/**
	 * Notifie qu'un item a été ajouté
	 * @param item L'item ajouté
	 */
	public void notifyItemAdded(BookItem item) {
		for (BookItemObserver itemObserver : getObservers()) {
			itemObserver.itemAdded(item);
		}
	}

	/**
	 * Notifie qu'un item a été supprimé
	 * @param item L'item supprimé
	 */
	public void notifyItemDeleted(BookItem item) {
		for (BookItemObserver itemObserver : getObservers()) {
			itemObserver.itemDeleted(item);
		}
	}

	/**
	 * Notifie qu'un item a été mis à jour
	 * @param oldItem L'ancien item
	 * @param newItem Le nouvel item
	 */
	public void notifyItemEdited(BookItem oldItem, BookItem newItem) {
		for (BookItemObserver itemObserver : getObservers()) {
			itemObserver.itemEdited(oldItem, newItem);
		}
	}

}
