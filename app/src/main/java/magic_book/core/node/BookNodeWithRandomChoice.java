package magic_book.core.node;

import java.util.List;
import magic_book.core.item.BookItem;

public class BookNodeWithRandomChoice extends AbstractBookNodeWithChoice{
	
	public BookNodeWithRandomChoice(int nbItemsAPrendre, List<BookItem> items, List<BookNodeLink> choices){
		super(nbItemsAPrendre, items, choices);
	}
	public List getRandomChoice(){
		return choices;
	}
}
