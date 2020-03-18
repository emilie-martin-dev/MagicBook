package magic_book.core.item;

public class BookItemMoney extends BookItem {
	
	private int amount;

	public BookItemMoney(String id, String nom, int amount) {
		super(id, nom);
		
		this.amount = amount;
	}

	public int getAmount() {
		return this.amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
	
}
