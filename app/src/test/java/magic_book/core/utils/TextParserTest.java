package magic_book.core.utils;

import magic_book.core.Character;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import magic_book.core.item.Item;

public class TextParserTest {

    @Test
    public void test_parseText() {
		String text = "## c ## est arrivée à bon port avec le {{ i1 }}. Le messager de ## c ##, ##  c2     ## en compagnie de ##c3## se tiennent devant vous. \\#\\# Pas bouger! \\#\\#";
		String expected = "Jennifer est arrivée à bon port avec le Sceptre. Le messager de Jennifer, Zeref en compagnie de Pluton se tiennent devant vous. \\#\\# Pas bouger! \\#\\#";
		
		Character c1 = new Character("c", "Jennifer", "", 1, 1, null, null, 1);
		Character c2 = new Character("c2", "Zeref", "", 1, 1, null, null, 1);
		Character c3 = new Character("c3", "Pluton", "", 1, 1, null, null, 1);
		Character c4 = new Character("c4", "Error", "", 1, 1, null, null, 1);
		Item i = new Item("i1", "Sceptre");
				
		Assert.assertEquals(expected, TextParser.parseText(text, Arrays.asList(i), Arrays.asList(c1, c2, c3, c4)));
    }
}
