package magic_book.core.item;

public class BookItemLink {
	
	private String id;
	private int amount;
	private int price;
	private int sellingPrice;
	private boolean auto;

	public BookItemLink(String id, int amount, int price, boolean auto, int selling_price) {
		this.id = id;
		this.amount = amount;
		this.price = price;
		this.auto = auto;
		this.sellingPrice = selling_price;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(int sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public boolean getAuto() {
		return auto;
	}

	public void setAuto(boolean auto) {
		this.auto = auto;
	}
	
}
