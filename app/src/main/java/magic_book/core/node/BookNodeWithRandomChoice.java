package magic_book.core.node;

import java.util.List;
import magic_book.core.item.BookItem;

public class BookNodeWithRandomChoice extends AbstractBookNodeWithChoice<BookNodeLinkRandom>{
	
	public BookNodeWithRandomChoice(String text, int nbItemsAPrendre, List<BookItem> items, List<BookNodeLinkRandom> choices){
		super(text, nbItemsAPrendre, items, choices);
	}
}
