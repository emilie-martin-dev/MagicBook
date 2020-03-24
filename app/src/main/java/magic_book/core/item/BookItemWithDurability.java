package magic_book.core.item;

import magic_book.core.Book;
import magic_book.core.file.json.ItemJson;
import magic_book.core.parser.Descriptible;

public class BookItemWithDurability extends BookItem implements Descriptible {

	private int durability;
	
	public BookItemWithDurability(String id, String nom, int durability) {
		super(id, nom);
		
		this.durability = durability;
	}
	
	@Override
	public String getDescription(Book book) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(super.getDescription(book));
		
		buffer.append("Durabilité : ");
		if(durability != -1)
			buffer.append(durability);
		else
			buffer.append("Infini");
		buffer.append("\n");
		
		return buffer.toString();
	}

	@Override
	public ItemJson toJson() {
		ItemJson itemJson = super.toJson();
		
		itemJson.setDurability(durability);
		
		return itemJson;
	}

	@Override
	public void fromJson(ItemJson json) {
		super.fromJson(json);
		
		if(json.getDurability() != null) {
			durability = json.getDurability();
		} else {
			durability = -1;
		}
	}
	
	public int getDurability() {
		return durability;
	}

	public void setDurability(int durability) {
		this.durability = durability;
	}

}
