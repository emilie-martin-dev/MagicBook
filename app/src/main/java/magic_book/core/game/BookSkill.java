package magic_book.core.game;

import magic_book.core.Book;
import magic_book.core.parser.Descriptible;


public class BookSkill implements Descriptible {
	
	private String id;
	private String name;

	public BookSkill(String id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public String getDescription(Book book) {
		return this.name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
