package magic_book.core.requirement;

import magic_book.core.game.BookCharacter;
import magic_book.core.game.BookState;
import org.junit.Assert;
import org.junit.Test;

public class RequirementSkillTest {
	
	@Test
	public void isStatisfied(){
		BookCharacter bookCharacter = new BookCharacter("", "", 0, 0, null, null, 0);
		bookCharacter.addSkill("eclair");

		BookState bookState = new BookState();
		bookState.setMainCharacter(bookCharacter);

		AbstractRequirement requirementOwn = new RequirementSkill("eclair");
		AbstractRequirement requirementNotOwn = new RequirementSkill("feu");

		Assert.assertTrue(requirementOwn.isSatisfied(bookState));
		Assert.assertFalse(requirementNotOwn.isSatisfied(bookState));
	}
}
