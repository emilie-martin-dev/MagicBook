package magic_book.core.node;

import java.util.List;
import magic_book.core.item.BookItem;

public class BookNodeWithRandomChoices extends AbstractBookNodeWithChoices<BookNodeLinkRandom>{
	
	public BookNodeWithRandomChoices(String text) {
		this(text, 0, null, null);
	}
	
	public BookNodeWithRandomChoices(String text, int nbItemsAPrendre, List<BookItem> items, List<BookNodeLinkRandom> choices) {
		super(text, nbItemsAPrendre, items, choices);
	}
}
