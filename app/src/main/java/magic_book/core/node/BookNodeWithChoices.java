package magic_book.core.node;

import java.util.List;
import magic_book.core.item.BookItem;


public class BookNodeWithChoices extends AbstractBookNodeWithChoices<BookNodeLink> {
	
	public BookNodeWithChoices(String text) {
		super(text, 0, null, null);
	}
	
	public BookNodeWithChoices(String text, int nbItemsAPrendre, List<BookItem> items, List<BookNodeLink> choices){
		super(text, nbItemsAPrendre, items, choices);
		
	}

}
