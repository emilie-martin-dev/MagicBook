package magic_book.core.game.character_creation;

import java.util.ArrayList;
import java.util.List;

import magic_book.core.Book;
import magic_book.core.file.JsonExportable;
import magic_book.core.file.json.CharacterCreationJson;
import magic_book.core.file.json.TypeJson;

public class CharacterCreationSkill extends AbstractCharacterCreation implements JsonExportable<CharacterCreationJson> {
	
	private int amountToPick;
	private List<String> skillLinks;
	
	public CharacterCreationSkill() {
		this("", null, -1);
	}
	
	public CharacterCreationSkill(String text, List<String> skillLinks, int amountToPick) {
		super(text);
		
		this.skillLinks = skillLinks;
		this.amountToPick = amountToPick;
		
		if(this.skillLinks == null)
			this.skillLinks = new ArrayList<>();
	}

	@Override
	public String getDescription(Book book) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(super.getText());
		buffer.append("\n");
		buffer.append("Choisissez ");
		buffer.append(amountToPick);
		buffer.append(" compétences : \n\n");
		
		for(int i = 0 ; i < skillLinks.size() ; i++) {
			buffer.append("- ");
			buffer.append(book.getSkills().get(skillLinks.get(i)).getName());
			buffer.append("\n");
		}
		
		return buffer.toString();
	}
	
	@Override
	public CharacterCreationJson toJson() {
		CharacterCreationJson characterCreationJson = super.toJson();
		
		characterCreationJson.setAmountToPick(amountToPick);
		characterCreationJson.setSkills(skillLinks);
		characterCreationJson.setText(getText());
		characterCreationJson.setType(TypeJson.SKILL);
		
		return characterCreationJson;
	}

	@Override
	public void fromJson(CharacterCreationJson json) {
		super.fromJson(json);
		
		this.setText(json.getText());
		this.setAmountToPick(json.getAmountToPick());
		this.setSkillLinks(json.getSkills());
	}
	
	public void addSkillLink(String skillLink) {
		this.skillLinks.add(skillLink);
	}
	
	public List<String> getSkillLinks() {
		return skillLinks;
	}

	public void setSkillLinks(List<String> skillLinks) {
		this.skillLinks = skillLinks;
	}

	public int getAmountToPick() {
		return amountToPick;
	}
	
	public void setAmountToPick(int amountToPick) {
		this.amountToPick = amountToPick;
	}
}
