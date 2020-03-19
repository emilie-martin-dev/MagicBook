package magic_book.core.game.character_creation;

import java.util.ArrayList;
import java.util.List;
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
