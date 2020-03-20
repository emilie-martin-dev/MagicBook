package magic_book.core.item;

import java.util.HashMap;
import magic_book.core.Book;
import magic_book.core.graph.node.AbstractBookNode;

public class BookItemHealing extends BookItemWithDurability {
	
	private int hp;

	public BookItemHealing(String id, String nom, Integer durability, int hp) {
		super(id, nom, durability);
		
		this.hp = hp;
	}

	public String getTextForBookText(Book book, HashMap<AbstractBookNode, Integer> nodesInv) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(super.getTextForBookText(book, nodesInv));
		
		buffer.append("Quantité de soins : ");
		buffer.append(hp);
		buffer.append("\n");
		
		return buffer.toString();
	}
	
	public int getHp() {
		return this.hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}
	
}
