package magic_book.core.requirement;

import java.util.HashMap;
import magic_book.core.Book;
import magic_book.core.game.BookState;
import magic_book.core.graph.node.AbstractBookNode;

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

	@Override
	public String getTextForBookText(Book book, HashMap<AbstractBookNode, Integer> nodesIndex) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("Vous devez posséder la compétence ");
		buffer.append(book.getSkills().get(skillId).getName());
		buffer.append("\n");
		
		return buffer.toString();
	}
	
	public String getSkillId() {
		return skillId;
	}

	public void setSkillId(String skillId) {
		this.skillId = skillId;
	}
	
}
