package magic_book.core.item;

import java.util.HashMap;
import magic_book.core.Book;
import magic_book.core.graph.node.AbstractBookNode;

public class BookItemDefense extends BookItemWithDurability {
	
	private int resistance;

	public BookItemDefense(String id, String nom, Integer durability, int resistance) {
		super(id, nom, durability);
		
		this.resistance = resistance;
	}
	
	public String getTextForBookText(Book book, HashMap<AbstractBookNode, Integer> nodesInv) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(super.getTextForBookText(book, nodesInv));
		
		buffer.append("Défense : ");
		buffer.append(resistance);
		buffer.append("\n");
		
		return buffer.toString();
	}

	public int getResistance() {
		return resistance;
	}

	public void setResistance(int resistance) {
		this.resistance = resistance;
	}
	
}
