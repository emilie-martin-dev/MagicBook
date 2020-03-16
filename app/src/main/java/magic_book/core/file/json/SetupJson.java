package magic_book.core.file.json;

import java.util.List;

public class SetupJson {

	private List<SkillJson> skills;
	private List<ItemJson> items;
	private List<CharacterJson> characters;

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
