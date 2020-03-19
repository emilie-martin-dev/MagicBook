package magic_book.core.item;


public class BookItemWithDurability extends BookItem {

	private Integer durability;
	
	public BookItemWithDurability(String id, String nom, Integer durability) {
		super(id, nom);
		
		this.durability = durability;
	}

	public Integer getDurability() {
		return durability;
	}

	public void setDurability(Integer durability) {
		this.durability = durability;
	}

}
