package magic_book.core;

import java.util.ArrayList;
import magic_book.core.BookCharacter;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import magic_book.core.item.BookItem;
import magic_book.core.node.BookNode;
import magic_book.core.node.BookNodeLink;
import magic_book.core.node.BookNodeType;

public class BookCharacterTest {

    @Test
	public void heal_test(){
		BookCharacter character = new BookCharacter("", "", "", 0, 5, null, null, 0);
		
		Assert.assertTrue(character.getHp() == character.getHpMax());
		character.heal(10);
		
		Assert.assertTrue(character.getHp() == character.getHpMax());
		character.damage(10);
		character.heal(5);
		
		Assert.assertTrue(character.getHp() == character.getHpMax());
	}
	
	
	
}