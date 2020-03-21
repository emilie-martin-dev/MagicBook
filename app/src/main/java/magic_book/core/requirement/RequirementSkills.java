package magic_book.core.requirement;

import magic_book.core.game.BookSkill;
import magic_book.core.game.BookState;

public class RequirementSkills extends AbstractRequirement{
		
	private BookSkill skill;
	
	public RequirementSkills(BookSkill skill){
		this.skill = skill;
	}
	
	@Override
	public boolean isSatisfied(BookState state) {
		for (String skillState : state.getMainCharacter().getSkills()){
			if(skill.getName() == skillState) {
				return true;
			}
		}
		return false;
	}
}
