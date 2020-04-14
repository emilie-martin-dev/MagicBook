package magic_book.core.file.json;

import java.util.Map;

/**
 * Représente un livre au format JSON
 */
public class BookJson {
	
	/**
	 * Le texte du prélude
	 */
	private String prelude;
	/**
	 * Les éléments constitutif du livre
	 */
	private SetupJson setup;
	/**
	 * Les différents paragraphe du livre
	 */
	private Map<Integer, SectionJson> sections;

	/**
	 * Retourne le texte du prélude
	 * @return Le texte du prélude
	 */
	public String getPrelude() {
		return prelude;
	}

	/**
	 * Change le texte du prélude
	 * @param prelude Le texte du prélude
	 */
	public void setPrelude(String prelude) {
		this.prelude = prelude;
	}

	/**
	 * Retourne les éléments constitutif du livre
	 * @return Les éléments constitutif du livre
	 */
	public SetupJson getSetup() {
		return setup;
	}

	/**
	 * Change les éléments constitutif du livre
	 * @param setup Les éléments constitutif du livre
	 */
	public void setSetup(SetupJson setup) {
		this.setup = setup;
	}

	/**
	 * Retourne les paragraphes du livre
	 * @return Les paragraphes du livre
	 */
	public Map<Integer, SectionJson> getSections() {
		return sections;
	}

	/**
	 * Change les paragraphes du livre
	 * @param sections Les paragraphes du livre
	 */
	public void setSections(Map<Integer, SectionJson> sections) {
		this.sections = sections;
	}

}
