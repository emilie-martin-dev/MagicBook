package magic_book.core.item;

public class Potion extends BookItem {
	
	private int vie;

	public Potion(String id, String nom, int vie) {
		super(id, nom);
		this.vie = vie;
	}

	public int getVie() {
		return this.vie;
	}

	public void setVie(int vie) {
		this.vie = vie;
	}
	
}
