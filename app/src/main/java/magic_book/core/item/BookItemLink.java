package magic_book.core.item;

import magic_book.core.Book;
import magic_book.core.file.JsonExportable;
import magic_book.core.file.json.ItemLinkJson;
import magic_book.core.parser.Descriptible;

public class BookItemLink implements Descriptible, JsonExportable<ItemLinkJson> {
	
	private String id;
	private int amount;
	private int price;
	private int sellingPrice;
	private boolean auto;

	public BookItemLink() {
		this("", 1, 0, false, 0);
	}
	
	public BookItemLink(String id, int amount, int price, boolean auto, int sellingPrice) {
		this.id = id;
		this.amount = amount;
		this.price = price;
		this.auto = auto;
		this.sellingPrice = sellingPrice;
	}

	public BookItemLink(BookItemLink bookItemLink) {
		this.id = bookItemLink.id;
		this.amount = bookItemLink.amount;
		this.price = bookItemLink.price;
		this.sellingPrice = bookItemLink.sellingPrice;
		this.auto = bookItemLink.auto;
	}
	
	@Override
	public ItemLinkJson toJson() {
		ItemLinkJson itemLinkJson = new ItemLinkJson();
		
		itemLinkJson.setId(id);
		
		if(amount != 1) 
			itemLinkJson.setAmount(amount);
		
		if(price != 0)
			itemLinkJson.setPrice(price);
		
		if(sellingPrice != 0)
			itemLinkJson.setSellingPrice(sellingPrice);
		
		if(auto)
			itemLinkJson.setAuto(true);
		
		return itemLinkJson;
	}

	@Override
	public void fromJson(ItemLinkJson json) {
		this.id = json.getId();
		
		this.amount = 1;
		if(json.getAmount() != null) {
			this.amount = json.getAmount();
		}
		
		this.price = 0;
		if(json.getPrice()!= null) {
			this.price = json.getPrice();
		}
		
		this.auto = false;
		if(json.isAuto()!= null) {
			this.auto = json.isAuto();
		}
		
		this.sellingPrice = 0;		
		if(json.getSellingPrice() != null) {
			this.sellingPrice = json.getSellingPrice();
		}
	}

	@Override
	public String getDescription(Book book) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(book.getItems().get(id).getDescription(book));
		if(amount != 1)  {
			buffer.append("Nombre : ");
			buffer.append(amount);
			buffer.append("\n");
		}
		
		if(price != -1)  {
			buffer.append("Prix d'achat : ");
			buffer.append(price);
			buffer.append("\n");
		}
		
		if(sellingPrice != -1)  {
			buffer.append("Prix de vente : ");
			buffer.append(sellingPrice);
			buffer.append("\n");
		}
		
		if(auto)  {
			buffer.append("Obligation de prendre l'item\n");
		}
		
		return buffer.toString();
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

	@Override
	public String toString() {
		return this.id;
	}
	
}
