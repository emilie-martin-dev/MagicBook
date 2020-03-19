package magic_book.core.item;

public class BookItemWeapon extends BookItemWithDurability {
	
	private int damage;

	public BookItemWeapon(String id, String nom, int durability, int damage) {
		super(id, nom, durability);
		
		this.damage = damage;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}
	
}
