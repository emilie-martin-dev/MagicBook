package magic_book.core.requirement;

import magic_book.core.game.BookState;

public abstract class AbstractRequirement {

	public abstract boolean isSatisfied(BookState state);
	
}
