package magic_book.observer.book;

import magic_book.core.item.BookItem;

public interface BookItemObserver {

	public void itemAdded(BookItem item);

	public void itemEdited(BookItem oldItem, BookItem newItem);

	public void itemDeleted(BookItem item);
	
}
