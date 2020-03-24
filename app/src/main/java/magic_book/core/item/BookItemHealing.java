package magic_book.core.item;

import magic_book.core.Book;
import magic_book.core.parser.Descriptible;

public class BookItemHealing extends BookItemWithDurability implements Descriptible {
	
	private int hp;

	public BookItemHealing(String id, String nom, Integer durability, int hp) {
		super(id, nom, durability);
		
		this.hp = hp;
	}

	@Override
	public String getDescription(Book book) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(super.getDescription(book));
		
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
