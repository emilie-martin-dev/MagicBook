package magic_book.core.requirement;

import java.util.HashMap;
import magic_book.core.Book;
import magic_book.core.game.BookState;
import magic_book.core.graph.node.AbstractBookNode;

public abstract class AbstractRequirement {

	public abstract boolean isSatisfied(BookState state);

	public abstract String getTextForBookText(Book book, HashMap<AbstractBookNode, Integer> nodesIndex);
	
}
