package magic_book.core.node;

import java.util.List;

public class BookNodeWithRandomChoices extends AbstractBookNodeWithChoices<BookNodeLinkRandom>{
	
	public BookNodeWithRandomChoices(String text) {
		this(text, 0, null, null, null);
	}
	
	public BookNodeWithRandomChoices(String text, Integer nbItemsAPrendre, List<BookItemsLink> itemLinks, List<BookItemsLink> shopItemLinks, List<BookNodeLinkRandom> choices) {
		super(text, nbItemsAPrendre, itemLinks, shopItemLinks, choices);
	}
}
