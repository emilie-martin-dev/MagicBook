package magic_book.core.file.json;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Représente les éléments constitutif du livre au format JSON
 */
public class SetupJson {

	/**
	 * La liste des étapes sur la création du personnage principal
	 */
	@SerializedName("character_creation")
	private List<CharacterCreationJson> characterCreation;
	/**
	 * La liste des skills
	 */
	private List<SkillJson> skills;
	/**
	 * La liste des items
	 */
	private List<ItemJson> items;
	/**
	 * La liste des personnages
	 */
	private List<CharacterJson> characters;

	/**
	 * Retourne la liste des étapes sur la création du personnage principal
	 * @return La liste des étapes sur la création du personnage principal
	 */
	public List<CharacterCreationJson> getCharacterCreation() {
		return characterCreation;
	}

	/**
	 * Change la liste des étapes sur la création du personnage principal
	 * @param characterCreation La liste des étapes sur la création du personnage principal
	 */
	public void setCharacterCreation(List<CharacterCreationJson> characterCreation) {
		this.characterCreation = characterCreation;
	}
	
	/**
	 * Retourne la liste des skills
	 * @return La liste des skills
	 */
	public List<SkillJson> getSkills() {
		return skills;
	}

	/**
	 * Change la liste des skills
	 * @param skills La liste des skills
	 */
	public void setSkills(List<SkillJson> skills) {
		this.skills = skills;
	}

	/**
	 * Retourne la liste des items
	 * @return La liste des items
	 */
	public List<ItemJson> getItems() {
		return items;
	}

	/**
	 * Change la liste des items
	 * @param items La liste des items
	 */
	public void setItems(List<ItemJson> items) {
		this.items = items;
	}

	/**
	 * Retourne la liste des personnages
	 * @return La liste des personnages
	 */
	public List<CharacterJson> getCharacters() {
		return characters;
	}

	/**
	 * Change la liste des personnages
	 * @param characters La liste des personnages
	 */
	public void setCharacters(List<CharacterJson> characters) {
		this.characters = characters;
	}

}
