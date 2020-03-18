package magic_book.core;

import magic_book.core.game.BookCharacter;
import org.junit.Assert;
import org.junit.Test;

public class BookCharacterTest {

    @Test
	public void heal_test(){
		BookCharacter character = new BookCharacter("", "", 0, 5, null, null, 0);
		
		Assert.assertTrue(character.getHp() == character.getHpMax());
		character.heal(10);
		
		Assert.assertTrue(character.getHp() == character.getHpMax());
		character.damage(10);
		character.heal(5);
		
		Assert.assertTrue(character.getHp() == character.getHpMax());
	}
	
}