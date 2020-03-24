package magic_book.core.item;

import magic_book.core.Book;
import magic_book.core.file.json.ItemJson;
import magic_book.core.file.json.ItemType;
import magic_book.core.parser.Descriptible;

public class BookItemWeapon extends BookItemWithDurability implements Descriptible {
	
	private int damage;

	public BookItemWeapon() {
		this("", "", 0, 0);
	}
	
	public BookItemWeapon(String id, String nom, int durability, int damage) {
		super(id, nom, durability);
		
		this.damage = damage;
	}
		
	@Override
	public String getDescription(Book book) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(super.getDescription(book));
		
		buffer.append("Dégats : ");
		buffer.append(damage);
		buffer.append("\n");
		
		return buffer.toString();
	}

	@Override
	public ItemJson toJson() {
		ItemJson itemJson = super.toJson();
		
		itemJson.setDamage(damage);
		itemJson.setItemType(ItemType.WEAPON);
		
		return itemJson;
	}

	@Override
	public void fromJson(ItemJson json) {
		super.fromJson(json); 
	
		damage = json.getDamage();
	}
	
	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}
	
}
