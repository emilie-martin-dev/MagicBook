package magic_book.core.item;

import magic_book.core.utils.Parsable;

public class BookItem implements Parsable {

	private String id;
	private String nom;

	public BookItem(String id, String nom) {
		this.id = id;
		this.nom = nom;
	}

	@Override
	public String getParsableId() {
		return this.id;
	}

	@Override
	public String getParsableText() {
		return this.nom;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}


	@Override
	public String toString() {
		return this.nom;
	}
}