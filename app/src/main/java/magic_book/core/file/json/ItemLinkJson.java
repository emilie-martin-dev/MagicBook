package magic_book.core.file.json;

import com.google.gson.annotations.SerializedName;


public class ItemLinkJson {
	
	private String id;
	private Integer amount;
	private Integer price;
	@SerializedName("selling_price")
	private Integer sellingPrice;
	private Boolean auto;

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

	public Boolean isAuto() {
		return auto;
	}

	public void setAuto(Boolean auto) {
		this.auto = auto;
	}

}
