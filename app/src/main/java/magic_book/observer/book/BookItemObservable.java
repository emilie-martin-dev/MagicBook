package magic_book.observer.book;

import magic_book.core.item.BookItem;
import magic_book.observer.Observable;

public class BookItemObservable extends Observable<BookItemObserver> {

	public void notifyItemAdded(BookItem item) {
		for (BookItemObserver itemObserver : getObservers()) {
			itemObserver.itemAdded(item);
		}
	}

	public void notifyItemDeleted(BookItem item) {
		for (BookItemObserver itemObserver : getObservers()) {
			itemObserver.itemDeleted(item);
		}
	}

	public void notifyItemEdited(BookItem oldItem, BookItem newItem) {
		for (BookItemObserver itemObserver : getObservers()) {
			itemObserver.itemEdited(oldItem, newItem);
		}
	}

}
