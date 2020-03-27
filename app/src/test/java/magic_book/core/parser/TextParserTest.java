package magic_book.core.parser;

import magic_book.core.parser.TextParser;
import magic_book.core.game.BookCharacter;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import magic_book.core.item.BookItem;

public class TextParserTest {

    @Test
    public void test_parseText() {
		String text = "## c ## est arrivée à bon port avec le {{ i1 }}. Le messager de ## c ##, ##  c2     ## en compagnie de ##c3## se tiennent devant vous. \\#\\# Pas bouger! \\#\\#";
		String expected = "Jennifer est arrivée à bon port avec le Sceptre. Le messager de Jennifer, Zeref en compagnie de Pluton se tiennent devant vous. \\#\\# Pas bouger! \\#\\#";
		
		HashMap<String, BookCharacter> characters = new HashMap<>();
		BookCharacter c1 = new BookCharacter("c", "Jennifer", 1, 1, null, null, 1);
		BookCharacter c2 = new BookCharacter("c2", "Zeref", 1, 1, null, null, 1);
		BookCharacter c3 = new BookCharacter("c3", "Pluton", 1, 1, null, null, 1);
		BookCharacter c4 = new BookCharacter("c4", "Error", 1, 1, null, null, 1);
		characters.put(c1.getId(), c1);
		characters.put(c2.getId(), c2);
		characters.put(c3.getId(), c3);
		characters.put(c4.getId(), c4);		
		
		HashMap<String, BookItem> items = new HashMap<>();
		BookItem i = new BookItem("i1", "Sceptre");
		items.put(i.getId(), i);
		
		Assert.assertEquals(expected, TextParser.parseText(text, items, characters));
    }
}
