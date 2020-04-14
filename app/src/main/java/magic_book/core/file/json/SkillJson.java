package magic_book.core.file.json;

/**
 * Représente un skill au format json
 */
public class SkillJson {
	
	/**
	 * L'id du skill
	 */
	private String id;

	/**
	 * Le nom du skill
	 */
	private String name;

	/**
	 * Retourne l'id du skill
	 * @return L'id du skill
	 */
	public String getId() {
		return id;
	}

	/**
	 * Change l'id du skill
	 * @param id L'id du skill
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Retrourne le nom du skill
	 * @return Le nom du skill
	 */
	public String getName() {
		return name;
	}

	/**
	 * CHange le nom du skill
	 * @param name Le nom du skill
	 */
	public void setName(String name) {
		this.name = name;
	}
	
}
