package magic_book.core.graph.node;

import java.util.List;
import magic_book.core.file.json.ChoiceJson;
import magic_book.core.file.json.SectionJson;
import magic_book.core.item.BookItemLink;
import magic_book.core.graph.node_link.BookNodeLink;

public class BookNodeWithChoices extends AbstractBookNodeWithChoices<BookNodeLink> {
	
	public BookNodeWithChoices() {
		this("");
	}
	
	public BookNodeWithChoices(String text) {
		super(text, 0, null, null, null);
	}
	
	public BookNodeWithChoices(String text, Integer nbItemsAPrendre, List<BookItemLink> itemLinks, List<BookItemLink> shopItemLinks, List<BookNodeLink> choices){
		super(text, nbItemsAPrendre, itemLinks, shopItemLinks, choices);
		
	}	

	@Override
	public void fromJson(SectionJson json) {
		super.fromJson(json);
				
		if(json.getChoices() != null) {
			for(ChoiceJson choiceJson : json.getChoices()) {		
				BookNodeLink nodeLink = new BookNodeLink();
				nodeLink.fromJson(choiceJson);
				
				addChoice(nodeLink);
			}
		}
	}

}
