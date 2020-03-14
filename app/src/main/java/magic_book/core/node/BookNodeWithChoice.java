package magic_book.core.node;

import java.util.ArrayList;
import java.util.List;
import magic_book.core.item.BookItem;


public class BookNodeWithChoice extends AbstractBookNodeWithChoice<BookNodeLink> {
	
	public BookNodeWithChoice(String text, int nbItemsAPrendre, List<BookItem> items, List<BookNodeLink> choices){
		super(text, nbItemsAPrendre, items, choices);
		
		if(this.choices == null)
			this.choices = new ArrayList<>();
	}
	
	@Override
	public boolean isTerminal() {
		return false;
	}

}
