package magic_book.core.game;

import magic_book.core.Book;
import magic_book.core.file.json.SkillJson;
import magic_book.core.file.JsonExportable;
import magic_book.core.parser.Descriptible;

/**
 * Création des compétences
 */
public class BookSkill implements Descriptible, JsonExportable<SkillJson> {
	
	/**
	 * Id de la compétence
	 */
	private String id;
	/**
	 * Nom de la compétence
	 */
	private String name;

	/**
	 * Initialisation des valeurs
	 */
	public BookSkill() {
		this("", "");
	}
	
	/**
	 * Modification du skill
	 * @param id ID de la compétence
	 * @param name Nom de la compétence
	 */
	public BookSkill(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	@Override
	public SkillJson toJson() {
		SkillJson skillJson = new SkillJson();
		
		skillJson.setId(id);
		skillJson.setName(name);
		
		return skillJson;
	}

	@Override
	public void fromJson(SkillJson json) {
		this.id = json.getId();
		this.name = json.getName();
	}
	

	@Override
	public String getDescription(Book book) {
		return this.name+"\n";
	}

	/**
	 * Donne l'ID de la compétence
	 * @return l'ID de la compétence
	 */
	public String getId() {
		return id;
	}

	/**
	 * Modifie la compétence
	 * @param id Nouvel ID de la compétence
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Done le nom de la compétence
	 * @return Nom de la compétence
	 */
	public String getName() {
		return name;
	}

	/**
	 * Modifie le nom de la compétence
	 * @param name Nouveau nom de la compétence
	 */
	public void setName(String name) {
		this.name = name;
	}
	
}
