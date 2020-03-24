package magic_book.core.requirement;

import magic_book.core.Book;
import magic_book.core.game.BookState;

public abstract class AbstractRequirement {

	public abstract boolean isSatisfied(BookState state);

	public abstract String getDescription(Book book);
}
