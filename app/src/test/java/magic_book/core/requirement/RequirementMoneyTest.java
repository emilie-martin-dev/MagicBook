package magic_book.core.requirement;

import magic_book.core.game.BookCharacter;
import magic_book.core.game.BookState;
import org.junit.Assert;
import org.junit.Test;

public class RequirementMoneyTest {
	
	@Test
	public void isStatisfied(){
		BookCharacter mainChar = new BookCharacter("", "", 0, 0, null, null, 0);
		mainChar.changeMoneyAmount("gold", 5);
		
		BookState bookState = new BookState();
		bookState.setMainCharacter(mainChar);
		
		AbstractRequirement requirementEnough = new RequirementMoney("gold", 1);
		AbstractRequirement requirementNotEnough = new RequirementMoney("gold", 100);
		AbstractRequirement requirementEquals = new RequirementMoney("gold", 5);
		
		Assert.assertTrue(requirementEnough.isSatisfied(bookState));
		Assert.assertTrue(requirementEquals.isSatisfied(bookState));
		Assert.assertFalse(requirementNotEnough.isSatisfied(bookState));
	}

}
