package magic_book.core.item;

import magic_book.core.parser.Parsable;

public class BookItem implements Parsable {

	private String id;
	private String name;

	public BookItem() {
		this("", "");
	}

	public BookItem(String id, String nom) {
		this.id = id;
		this.name = nom;
	}

	@Override
	public String getParsableId() {
		return this.id;
	}

	@Override
	public String getParsableText() {
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


	@Override
	public String toString() {
		return this.name;
	}
}