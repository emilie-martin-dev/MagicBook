package magic_book.core.node;

import java.util.List;
import magic_book.core.item.BookItem;


public abstract class AbstractBookNodeWithChoice <T> extends AbstractBookNode{
	protected int nbItemsAPrendre;
	protected List<BookItem> items;
	protected List<T> choices;
}
