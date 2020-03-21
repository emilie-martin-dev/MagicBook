package magic_book.core.requirement;

import magic_book.core.game.BookState;

public class RequirementSkills extends AbstractRequirement{
		
	private String skillId;
	
	public RequirementSkills(String skillId){
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
}
