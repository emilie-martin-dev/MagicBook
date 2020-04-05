package magic_book.core.game.character_creation;

import java.util.ArrayList;
import java.util.List;
import magic_book.core.Book;
import magic_book.core.file.json.CharacterCreationJson;
import magic_book.core.file.json.ItemLinkJson;
import magic_book.core.file.json.TypeJson;
import magic_book.core.item.BookItemLink;

public class CharacterCreationItem extends AbstractCharacterCreation {

	private int amountToPick;
	private List<BookItemLink> itemLinks;
	
	public CharacterCreationItem() {
		this("", null, -1);
	}
	
	public CharacterCreationItem(String text, List<BookItemLink> itemLinks, int amountToPick) {
		super(text);
		
		this.itemLinks = itemLinks;
		this.amountToPick = amountToPick;
		
		if(this.itemLinks == null)
			this.itemLinks = new ArrayList<>();
	}
	
	@Override
	public String getDescription(Book book) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(super.getDescription(book));
		buffer.append("\n");
		buffer.append("Choisissez ");
		buffer.append(amountToPick);
		buffer.append(" items : \n\n");
		
		for(int i = 0 ; i < itemLinks.size() ; i++) {
			buffer.append(itemLinks.get(i).getDescription(book));
			if(i < itemLinks.size() - 1) 
				buffer.append("\n");
		}
		
		return buffer.toString();
	}
	
	public CharacterCreationJson toJson() {
		CharacterCreationJson characterCreationJson = super.toJson();
		
		characterCreationJson.setAmountToPick(amountToPick);
		
		characterCreationJson.setItems(new ArrayList<>());
		for(BookItemLink itemLink : itemLinks) {
			characterCreationJson.getItems().add(itemLink.toJson());
		}
		
		characterCreationJson.setType(TypeJson.ITEM);
		
		return characterCreationJson;
	}

	@Override
	public void fromJson(CharacterCreationJson json) {
		super.fromJson(json);
		
		this.itemLinks.clear();
		
		if(json.getItems() != null) {
			for(ItemLinkJson itemLinkJson : json.getItems()) {
				BookItemLink bookItemsLink = new BookItemLink();
				bookItemsLink.fromJson(itemLinkJson);

				itemLinks.add(bookItemsLink);
			}
		}

		amountToPick = json.getAmountToPick();
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
