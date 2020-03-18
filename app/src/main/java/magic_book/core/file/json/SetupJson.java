package magic_book.core.file.json;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class SetupJson {

	@SerializedName("character_creation")
	private List<CharacterCreationJson> characterCreation;
	private List<SkillJson> skills;
	private List<ItemJson> items;
	private List<CharacterJson> characters;

	public List<CharacterCreationJson> getCharacterCreation() {
		return characterCreation;
	}

	public void setCharacterCreation(List<CharacterCreationJson> characterCreation) {
		this.characterCreation = characterCreation;
	}
	
	public List<SkillJson> getSkills() {
		return skills;
	}

	public void setSkills(List<SkillJson> skills) {
		this.skills = skills;
	}

	public List<ItemJson> getItems() {
		return items;
	}

	public void setItems(List<ItemJson> items) {
		this.items = items;
	}

	public List<CharacterJson> getCharacters() {
		return characters;
	}

	public void setCharacters(List<CharacterJson> characters) {
		this.characters = characters;
	}

}
