package magic_book.core.item;

public class Arme extends BookItem {
	
	private int degat;

	public Arme(String id, String nom, int degat) {
		super(id, nom);
		this.degat = degat;
	}

	public int getDegat() {
		return degat;
	}

	public void setDegat(int degat) {
		this.degat = degat;
	}
	
}
