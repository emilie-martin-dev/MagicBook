package magic_book.core.utils;

import magic_book.core.game.BookCharacter;
import magic_book.core.game.BookState;
import magic_book.core.item.BookItem;
import magic_book.core.requirement.AbstractRequirement;
import magic_book.core.requirement.RequirementItem;
import org.junit.Assert;
import org.junit.Test;

public class RequirementItemTest {
	@Test
    public void isStatisfied_itemExists(){
        BookCharacter bookCharacter = new BookCharacter("", "", 0, 0, null, null, 0);
        bookCharacter.addItem("Clé");
        
        BookState bookState = new BookState();
        bookState.setMainCharacter(bookCharacter);
        
		BookItem bookItemCle = new BookItem("1", "Clé");
		BookItem bookItemHache = new BookItem("2", "Hache");
		
        AbstractRequirement requirementEnough = new RequirementItem(bookItemCle);
		AbstractRequirement requirementNotEnough = new RequirementItem(bookItemHache);
		
		Assert.assertTrue(requirementEnough.isSatisfied(bookState));
        Assert.assertFalse(requirementNotEnough.isSatisfied(bookState));
	}
}
