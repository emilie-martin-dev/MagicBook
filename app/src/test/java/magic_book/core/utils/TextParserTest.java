package magic_book.core.utils;

import magic_book.core.parser.TextParser;
import magic_book.core.game.BookCharacter;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import magic_book.core.item.BookItem;

public class TextParserTest {

    @Test
    public void test_parseText() {
		String text = "## c ## est arrivée à bon port avec le {{ i1 }}. Le messager de ## c ##, ##  c2     ## en compagnie de ##c3## se tiennent devant vous. \\#\\# Pas bouger! \\#\\#";
		String expected = "Jennifer est arrivée à bon port avec le Sceptre. Le messager de Jennifer, Zeref en compagnie de Pluton se tiennent devant vous. \\#\\# Pas bouger! \\#\\#";
		
		BookCharacter c1 = new BookCharacter("c", "Jennifer", 1, 1, null, null, 1);
		BookCharacter c2 = new BookCharacter("c2", "Zeref", 1, 1, null, null, 1);
		BookCharacter c3 = new BookCharacter("c3", "Pluton", 1, 1, null, null, 1);
		BookCharacter c4 = new BookCharacter("c4", "Error", 1, 1, null, null, 1);
		BookItem i = new BookItem("i1", "Sceptre");
				
		Assert.assertEquals(expected, TextParser.parseText(text, Arrays.asList(i), Arrays.asList(c1, c2, c3, c4)));
    }
}
