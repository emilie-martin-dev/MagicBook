package magic_book.core.requirement;

import magic_book.core.game.BookCharacter;
import magic_book.core.game.BookState;
import magic_book.test.AbstractTest;
import org.junit.Assert;
import org.junit.Test;

public class RequirementItemTest extends AbstractTest {
	@Test
	public void isStatisfied(){
		BookCharacter bookCharacter = new BookCharacter("", "", 0, 0, null, null, 0);
		bookCharacter.addItem("cle");

		BookState bookState = new BookState();
		bookState.setMainCharacter(bookCharacter);

		AbstractRequirement requirementOwn = new RequirementItem("cle");
		AbstractRequirement requirementNotOwn = new RequirementItem("hache");

		Assert.assertTrue(requirementOwn.isSatisfied(bookState));
		Assert.assertFalse(requirementNotOwn.isSatisfied(bookState));
	}
}
