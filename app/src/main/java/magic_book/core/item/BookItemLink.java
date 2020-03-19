package magic_book.core.item;

public class BookItemLink {
	
	private String id;
	private Integer amount;
	private Integer price;
	private Integer sellingPrice;
	private Boolean auto;

	public BookItemLink(String id, Integer amount, Integer price, Boolean auto, Integer selling_price) {
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

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(Integer sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public Boolean getAuto() {
		return auto;
	}

	public void setAuto(Boolean auto) {
		this.auto = auto;
	}
	
}
