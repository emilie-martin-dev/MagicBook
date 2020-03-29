package magic_book.core.graph.node;

import java.util.ArrayList;
import java.util.List;
import magic_book.core.game.BookCharacter;
import magic_book.core.game.BookState;
import magic_book.core.graph.node_link.BookNodeLink;
import magic_book.core.graph.node_link.BookNodeLinkRandom;
import magic_book.core.item.BookItemWeapon;
import magic_book.core.requirement.AbstractRequirement;
import magic_book.core.requirement.RequirementItem;
import org.junit.Assert;
import org.junit.Test;


public class BookNodeWithRandomChoiceTest {
	
	@Test
	public void getRandomChoices(){
		BookNodeLinkRandom bookNodeLinkRandomChance1;
		BookNodeLinkRandom bookNodeLinkRandomChance2;
		List<BookNodeLinkRandom> bookNodeLinkRandom ;
		BookNodeWithRandomChoices bookNodeWithRandomChoices;
				
		BookCharacter bookCharacter = new BookCharacter("0", "", 0, 5, null, null, 0);

		BookState bookState = new BookState();
		bookState.setMainCharacter(bookCharacter);
		
		bookNodeLinkRandomChance1 = new BookNodeLinkRandom("", 0, null, 50);
		bookNodeLinkRandomChance2 = new BookNodeLinkRandom("", 0, null, 1);
		
		bookNodeLinkRandom = new ArrayList();
		bookNodeLinkRandom.add(bookNodeLinkRandomChance1);
		bookNodeLinkRandom.add(bookNodeLinkRandomChance2);
		
		bookNodeWithRandomChoices = new BookNodeWithRandomChoices("", 0, null, null, bookNodeLinkRandom);
	
		Assert.assertTrue(bookNodeWithRandomChoices.getRandomChoices(bookState) == bookNodeLinkRandomChance1);
		

		bookState = new BookState();
		bookState.setMainCharacter(bookCharacter);
	
		bookNodeLinkRandomChance1 = new BookNodeLinkRandom("", 0, null, 1);
		bookNodeLinkRandomChance2 = new BookNodeLinkRandom("", 0, null, 50);
		
		bookNodeLinkRandom = new ArrayList();
		bookNodeLinkRandom.add(bookNodeLinkRandomChance1);
		bookNodeLinkRandom.add(bookNodeLinkRandomChance2);
		
		bookNodeWithRandomChoices = new BookNodeWithRandomChoices("", 0, null, null, bookNodeLinkRandom);
		Assert.assertTrue(bookNodeWithRandomChoices.getRandomChoices(bookState) == bookNodeLinkRandomChance2);
		
		
		
		
		bookState = new BookState();
		bookState.setMainCharacter(bookCharacter);
		
		BookItemWeapon bookItemWeapon = new BookItemWeapon("arme", "Aiguille", 2, 2);
		
		RequirementItem requirementItem = new RequirementItem(bookItemWeapon.getId());
		
		List<AbstractRequirement> listRequirements = new ArrayList();
		listRequirements.add(requirementItem);
		
		List<List<AbstractRequirement>> requirements = new ArrayList();
		requirements.add(listRequirements);
		
		bookNodeLinkRandomChance1 = new BookNodeLinkRandom("", 0, requirements, 5);
		bookNodeLinkRandomChance2 = new BookNodeLinkRandom("", 0, null, 2);
		
		bookNodeLinkRandom = new ArrayList();
		bookNodeLinkRandom.add(bookNodeLinkRandomChance1);
		bookNodeLinkRandom.add(bookNodeLinkRandomChance2);
		
		bookNodeWithRandomChoices = new BookNodeWithRandomChoices("", 0, null, null, bookNodeLinkRandom);
		Assert.assertTrue(bookNodeWithRandomChoices.getRandomChoices(bookState) == bookNodeLinkRandomChance2);
	}
}
