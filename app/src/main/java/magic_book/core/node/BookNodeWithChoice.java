package magic_book.core.node;

import java.util.List;
import magic_book.core.item.BookItem;


public class BookNodeWithChoice extends AbstractBookNodeWithChoice<BookNodeLink> {
	
	public BookNodeWithChoice(String text, int nbItemsAPrendre, List<BookItem> items, List<BookNodeLink> choices){
		super(text, nbItemsAPrendre, items, choices);
		
	}

}
