package magic_book.core.game.character_creation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import magic_book.core.Book;
import magic_book.core.graph.node.AbstractBookNode;
import magic_book.core.item.BookItemLink;

public class CharacterCreationItem extends AbstractCharacterCreation {

	private int amountToPick;
	private List<BookItemLink> itemLinks;
	
	public CharacterCreationItem(String text, List<BookItemLink> itemLinks, int amountToPick) {
		super(text);
		
		this.itemLinks = itemLinks;
		this.amountToPick = amountToPick;
		
		if(this.itemLinks == null)
			this.itemLinks = new ArrayList<>();
	}
	
	@Override
	public String getTextForBookText(Book book, HashMap<AbstractBookNode, Integer> nodesInv) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(super.getText());
		buffer.append("\n");
		buffer.append("Choisissez ");
		buffer.append(amountToPick);
		buffer.append(" items : \n\n");
		
		for(int i = 0 ; i < itemLinks.size() ; i++) {
			buffer.append(itemLinks.get(i).getTextForBookText(book, nodesInv));
			if(i < itemLinks.size() - 1) 
				buffer.append("\n");
		}
		
		return buffer.toString();
	}
	
	public void addItemLink(BookItemLink itemLink) {
		this.itemLinks.add(itemLink);
	}

	
	public List<BookItemLink> getItemLinks() {
		return itemLinks;
	}

	public void setItemLinks(List<BookItemLink> itemLinks) {
		this.itemLinks = itemLinks;
	}

	public int getAmountToPick() {
		return amountToPick;
	}

	public void setAmountToPick(int amountToPick) {
		this.amountToPick = amountToPick;
	}

}
