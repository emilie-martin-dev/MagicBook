package magic_book.core.item;

import magic_book.core.Book;
import magic_book.core.parser.Descriptible;

public class BookItemDefense extends BookItemWithDurability implements Descriptible {
	
	private int resistance;

	public BookItemDefense(String id, String nom, Integer durability, int resistance) {
		super(id, nom, durability);
		
		this.resistance = resistance;
	}
	
	@Override
	public String getDescription(Book book) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(super.getDescription(book));
		
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
