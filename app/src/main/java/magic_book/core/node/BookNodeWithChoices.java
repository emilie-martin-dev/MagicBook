package magic_book.core.node;

import java.util.List;

public class BookNodeWithChoices extends AbstractBookNodeWithChoices<BookNodeLink> {
	
	public BookNodeWithChoices(String text) {
		super(text, 0, null, null, null);
	}
	
	public BookNodeWithChoices(String text, Integer nbItemsAPrendre, List<BookItemLink> itemLinks, List<BookItemLink> shopItemLinks, List<BookNodeLink> choices){
		super(text, nbItemsAPrendre, itemLinks, shopItemLinks, choices);
		
	}

}
