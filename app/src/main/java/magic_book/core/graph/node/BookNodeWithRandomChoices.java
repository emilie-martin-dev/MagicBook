package magic_book.core.graph.node;

import java.util.List;
import magic_book.core.item.BookItemLink;
import magic_book.core.graph.node_link.BookNodeLinkRandom;

public class BookNodeWithRandomChoices extends AbstractBookNodeWithChoices<BookNodeLinkRandom>{
	
	public BookNodeWithRandomChoices(String text) {
		this(text, 0, null, null, null);
	}
	
	public BookNodeWithRandomChoices(String text, Integer nbItemsAPrendre, List<BookItemLink> itemLinks, List<BookItemLink> shopItemLinks, List<BookNodeLinkRandom> choices) {
		super(text, nbItemsAPrendre, itemLinks, shopItemLinks, choices);
	}
}
