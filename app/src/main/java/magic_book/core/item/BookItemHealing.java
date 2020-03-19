package magic_book.core.item;

public class BookItemHealing extends BookItemWithDurability {
	
	private int hp;

	public BookItemHealing(String id, String nom, String itemTypeChoices, Integer durability, int hp) {
		super(id, nom, itemTypeChoices, durability);
		
		this.hp = hp;
	}

	public int getHp() {
		return this.hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}
	
}
