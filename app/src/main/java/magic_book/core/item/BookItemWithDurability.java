package magic_book.core.item;

import java.util.HashMap;
import magic_book.core.Book;
import magic_book.core.graph.node.AbstractBookNode;


public class BookItemWithDurability extends BookItem {

	private int durability;
	
	public BookItemWithDurability(String id, String nom, int durability) {
		super(id, nom);
		
		this.durability = durability;
	}
	
	public String getTextForBookText(Book book, HashMap<AbstractBookNode, Integer> nodesInv) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(super.getTextForBookText(book, nodesInv));
		
		buffer.append("Durabilité : ");
		if(durability != -1)
			buffer.append(durability);
		else
			buffer.append("Infini");
		buffer.append("\n");
		
		return buffer.toString();
	}

	public int getDurability() {
		return durability;
	}

	public void setDurability(int durability) {
		this.durability = durability;
	}

}
