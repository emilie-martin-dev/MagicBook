package magic_book.core.requirement;

import magic_book.core.game.BookState;

public class RequirementSkill extends AbstractRequirement{
		
	private String skillId;
	
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

	public String getSkillId() {
		return skillId;
	}

	public void setSkillId(String skillId) {
		this.skillId = skillId;
	}
	
}
