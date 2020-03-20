package magic_book.core.item;

import java.util.HashMap;
import magic_book.core.Book;
import magic_book.core.graph.node.AbstractBookNode;

public class BookItemWeapon extends BookItemWithDurability {
	
	private int damage;

	public BookItemWeapon(String id, String nom, int durability, int damage) {
		super(id, nom, durability);
		
		this.damage = damage;
	}
		
	public String getTextForBookText(Book book, HashMap<AbstractBookNode, Integer> nodesInv) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(super.getTextForBookText(book, nodesInv));
		
		buffer.append("Dégats : ");
		buffer.append(damage);
		buffer.append("\n");
		
		return buffer.toString();
	}
	
	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}
	
}
