package magic_book.core.requirement;

import magic_book.core.Book;
import magic_book.core.file.json.RequirementJson;
import magic_book.core.file.json.TypeJson;
import magic_book.core.game.BookState;

/**
 * Requiert d'avoir un skill 
 */
public class RequirementSkill extends AbstractRequirement {
		
	/**
	 * Id du skill
	 */
	private String skillId;
	
	public RequirementSkill() {
		this("");
	}
	
	public RequirementSkill(String skillId){
		this.skillId = skillId;
	}
	
	@Override
	public boolean isSatisfied(BookState state) {
		for (String s : state.getMainCharacter().getSkills()){
			if(s.equals(skillId)) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public String getDescription(Book book) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("Vous devez posséder la compétence ");
		buffer.append(book.getSkills().get(skillId).getName());
		buffer.append("\n");
		
		return buffer.toString();		
	}
	
	@Override
	public RequirementJson toJson() {
		RequirementJson requirementJson = new RequirementJson();
		
		requirementJson.setId(skillId);
		requirementJson.setType(TypeJson.SKILL);
		
		return requirementJson;
	}

	@Override
	public void fromJson(RequirementJson json) {
		skillId = json.getId();
	}

	
	/**
	 * Donne l'id du skill
	 * @return l'id du skill
	 */
	public String getSkillId() {
		return skillId;
	}

	/**
	 * Change l'id du skill
	 * @param skillId Le nouvel id du skill
	 */
	public void setSkillId(String skillId) {
		this.skillId = skillId;
	}
	
}
