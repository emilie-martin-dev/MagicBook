package magic_book.core.item;


public class BookItemWithDurability extends BookItem {

	private int durability;
	
	public BookItemWithDurability(String id, String nom, int durability) {
		super(id, nom);
		
		this.durability = durability;
	}

	public int getDurability() {
		return durability;
	}

	public void setDurability(int durability) {
		this.durability = durability;
	}

}
