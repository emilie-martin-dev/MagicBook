package magic_book.core.game.character_creation;

import java.util.ArrayList;
import java.util.List;


public class CharacterCreationSkill extends AbstractCharacterCreation {
	
	private int amountToPick;
	private List<String> skillLinks;
	
	public CharacterCreationSkill(String text, List<String> skillLinks, int amountToPick) {
		super(text);
		
		this.skillLinks = skillLinks;
		this.amountToPick = amountToPick;
		
		if(this.skillLinks == null)
			this.skillLinks = new ArrayList<>();
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
