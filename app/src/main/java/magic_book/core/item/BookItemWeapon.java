package magic_book.core.item;

public class BookItemWeapon extends BookItemWithDurability {
	
	private int damage;

	public BookItemWeapon(String id, String nom, String itemTypeChoices, Integer durability, int damage) {
		super(id, nom, itemTypeChoices, durability);
		
		this.damage = damage;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}
	
}
