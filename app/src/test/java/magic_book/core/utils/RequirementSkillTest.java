package magic_book.core.utils;

import magic_book.core.game.BookCharacter;
import magic_book.core.game.BookSkill;
import magic_book.core.game.BookState;
import magic_book.core.item.BookItem;
import magic_book.core.requirement.AbstractRequirement;
import magic_book.core.requirement.RequirementItem;
import magic_book.core.requirement.RequirementSkills;
import org.junit.Assert;
import org.junit.Test;

public class RequirementSkillTest {
	@Test
    public void isStatisfied_skillExists(){
        BookCharacter bookCharacter = new BookCharacter("", "", 0, 0, null, null, 0);
        bookCharacter.addSkill("Eclair");
        
        BookState bookState = new BookState();
        bookState.setMainCharacter(bookCharacter);
        
		BookSkill bookSkillEclair = new BookSkill("1", "Eclair");
		BookSkill bookSkillFeu = new BookSkill("2", "Feu");
		
        AbstractRequirement requirementEnough = new RequirementSkills(bookSkillEclair);
		AbstractRequirement requirementNotEnough = new RequirementSkills(bookSkillFeu);
		
		Assert.assertTrue(requirementEnough.isSatisfied(bookState));
        Assert.assertFalse(requirementNotEnough.isSatisfied(bookState));
	}
}
