package magic_book.core.game.character_creation;

import magic_book.core.Book;
import magic_book.core.file.JsonExportable;
import magic_book.core.file.json.CharacterCreationJson;
import magic_book.core.parser.Descriptible;

public abstract class AbstractCharacterCreation implements Descriptible, JsonExportable<CharacterCreationJson> {

	private String text;

	public AbstractCharacterCreation(String text) {
		this.text = text;
	}
	
	@Override
	public CharacterCreationJson toJson() {
		CharacterCreationJson characterCreationJson = new CharacterCreationJson();
		
		characterCreationJson.setText(getText());
		
		return characterCreationJson;
	}

	@Override
	public void fromJson(CharacterCreationJson json) {
		this.setText(json.getText());
	}
	
	public String getDescription(Book book) {
		return this.text + "\n";
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
