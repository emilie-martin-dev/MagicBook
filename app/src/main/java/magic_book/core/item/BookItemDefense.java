package magic_book.core.item;

import magic_book.core.Book;
import magic_book.core.file.json.ItemJson;
import magic_book.core.file.json.ItemType;
import magic_book.core.parser.Descriptible;

public class BookItemDefense extends BookItemWithDurability implements Descriptible {
	
	private int resistance;

	public BookItemDefense() {
		this("", "", 0, 0);
	}
	
	public BookItemDefense(String id, String nom, int durability, int resistance) {
		super(id, nom, durability);
		
		this.resistance = resistance;
	}
	
	@Override
	public String getDescription(Book book) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(super.getDescription(book));
		
		buffer.append("Défense : ");
		buffer.append(resistance);
		buffer.append("\n");
		
		return buffer.toString();
	}

	@Override
	public ItemJson toJson() {
		ItemJson itemJson = super.toJson();
		
		itemJson.setResistance(resistance);
		itemJson.setItemType(ItemType.DEFENSE);
		
		return itemJson;
	}

	@Override
	public void fromJson(ItemJson json) {
		super.fromJson(json);
		
		resistance = json.getResistance();
	}

	public int getResistance() {
		return resistance;
	}

	public void setResistance(int resistance) {
		this.resistance = resistance;
	}
	
}
