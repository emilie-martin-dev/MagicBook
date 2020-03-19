package magic_book.core.game.character_creation;


public abstract class AbstractCharacterCreation {

	private String text;

	public AbstractCharacterCreation(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
