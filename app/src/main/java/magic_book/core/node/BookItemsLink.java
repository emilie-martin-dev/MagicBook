package magic_book.core.node;

public class BookItemsLink {
	
	private String id;
	private int amount;
	private int price;
	private int sellingPrice;
	private boolean auto;

	public BookItemsLink(String id, int amount, int price, boolean auto, int selling_price) {
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
	
	public boolean isAuto() {
		return auto;
	}

	public void setAuto(boolean auto) {
		this.auto = auto;
	}

	public int getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(int sellingPrice) {
		this.sellingPrice = sellingPrice;
	}
	
}
