package magic_book.core.game.character_creation;

import magic_book.core.Book;
import magic_book.core.file.JsonExportable;
import magic_book.core.file.json.CharacterCreationJson;
import magic_book.core.parser.Descriptible;

/**
 * Classe de base pour la conception d'une étape à la "Conception d'un personnage"
 */
public abstract class AbstractCharacterCreation implements Descriptible, JsonExportable<CharacterCreationJson> {

	/**
	 * Texte de la partie de la conception
	 */
	private String text;

	/**
	 * Constructeur de base
	 * @param text Le texte à afficher
	 */
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
	
	@Override
	public String getDescription(Book book) {
		return this.text + "\n";
	}
	
	/**
	 * Donne le texte
	 * @return Texte
	 */
	public String getText() {
		return text;
	}

	/**
	 * Modifie le texte
	 * @param text Nouveau texte
	 */
	public void setText(String text) {
		this.text = text;
	}
	
}
