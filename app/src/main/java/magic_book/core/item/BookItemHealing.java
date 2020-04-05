package magic_book.core.item;

import magic_book.core.Book;
import magic_book.core.file.json.ItemJson;
import magic_book.core.file.json.ItemType;

public class BookItemHealing extends BookItemWithDurability {
	
	private int hp;
	
	public BookItemHealing() {
		this("", "", 0, 0);
	}

	public BookItemHealing(String id, String nom, int durability, int hp) {
		super(id, nom, durability);
		
		this.hp = hp;
	}

	@Override
	public String getDescription(Book book) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(super.getDescription(book));
		
		buffer.append("Quantité de soins : ");
		buffer.append(hp);
		buffer.append("\n");
		
		return buffer.toString();
	}

	@Override
	public ItemJson toJson() {
		ItemJson itemJson = super.toJson();
		
		itemJson.setHp(hp);
		itemJson.setItemType(ItemType.HEALING);
		
		return itemJson;
	}

	@Override
	public void fromJson(ItemJson json) {
		super.fromJson(json);

		hp = json.getHp();
	}
	
	public int getHp() {
		return this.hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}
	
}
