package magic_book.core.game.character_creation;

import magic_book.core.Book;
import magic_book.core.file.JsonExportable;
import magic_book.core.file.json.CharacterCreationJson;
import magic_book.core.parser.Descriptible;

public abstract class AbstractCharacterCreation implements Descriptible, JsonExportable<CharacterCreationJson> {

	/**
	 * Texte lié
	 */
	private String text;

	/**
	 * Création du texte lié à tout les skill et items ajouté
	 * @param text 
	 */
	public AbstractCharacterCreation(String text) {
		this.text = text;
	}
	
	/**
	 * Permet de transformer tout les CharacterCreation en Json
	 * @return Character création écrit en Json
	 */
	@Override
	public CharacterCreationJson toJson() {
		CharacterCreationJson characterCreationJson = new CharacterCreationJson();
		
		characterCreationJson.setText(getText());
		
		return characterCreationJson;
	}

	/**
	 * Permet de transformer le CharacterCreation écrit en Json en CharacterCreation
	 * @param json 
	 */
	@Override
	public void fromJson(CharacterCreationJson json) {
		this.setText(json.getText());
	}
	
	/**
	 * Donne et permet d'afficher le texte
	 * @param book Livre contenant toute les informations
	 * @return 
	 */
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
