package magic_book.core.item;

import java.util.HashMap;
import magic_book.core.Book;
import magic_book.core.graph.node.AbstractBookNode;

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

	public String getTextForBookText(Book book, HashMap<AbstractBookNode, Integer> nodesInv) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(book.getItems().get(id).getTextForBookText(book, nodesInv));
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
	
}
