package magic_book.core.item;

public class BookItemDefense extends BookItemWithDurability {
	
	private int resistance;

	public BookItemDefense(String id, String nom, Integer durability, int resistance) {
		super(id, nom, durability);
		
		this.resistance = resistance;
	}

	public int getResistance() {
		return resistance;
	}

	public void setResistance(int resistance) {
		this.resistance = resistance;
	}
	
}
