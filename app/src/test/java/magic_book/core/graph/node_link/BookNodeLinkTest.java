package magic_book.core.graph.node_link;

import java.util.ArrayList;
import java.util.List;
import magic_book.core.game.BookCharacter;
import magic_book.core.game.BookState;
import magic_book.core.requirement.AbstractRequirement;
import magic_book.core.requirement.RequirementItem;
import magic_book.core.requirement.RequirementMoney;
import magic_book.core.requirement.RequirementSkill;
import org.junit.Assert;
import org.junit.Test;

public class BookNodeLinkTest {
	
	@Test
	public void isAvailaible() {
		// Constantes
		String itemName = "cle_succes";
		String goldName = "gold";
		
		// Création du state
		BookState state = new BookState();
		BookCharacter character = new BookCharacter();
		character.addItem(itemName);
		character.changeMoneyAmount(goldName, 20);
		state.setMainCharacter(character);
		
		//Création des requirements
		RequirementMoney moneyRequirement = new RequirementMoney(goldName, 5);
		RequirementItem itemRequirement = new RequirementItem(itemName);
		RequirementSkill skillRequirent = new RequirementSkill("Foudre");
		
		ArrayList<List<AbstractRequirement>> requirementsSingle = new ArrayList<>();
		requirementsSingle.add(new ArrayList<>());
		requirementsSingle.get(0).add(moneyRequirement);
		requirementsSingle.get(0).add(itemRequirement);
		
		ArrayList<List<AbstractRequirement>> requirementsMany = new ArrayList<>();
		requirementsMany.add(new ArrayList<>());
		requirementsMany.get(0).add(skillRequirent);
		requirementsMany.add(new ArrayList<>());
		requirementsMany.get(1).add(moneyRequirement);
		requirementsMany.get(1).add(itemRequirement);
		
		// On effectue les tests
		BookNodeLink nodeLink = new BookNodeLink();
		
		nodeLink.setRequirements(new ArrayList<>());
		Assert.assertTrue("Test sans prérequis", nodeLink.isAvailable(state));
		
		// [[true, true]]
		nodeLink.setRequirements(requirementsSingle);
		Assert.assertTrue("Test 1 liste de prérequis ok", nodeLink.isAvailable(state));
		
		// [[false], [true, true]]
		nodeLink.setRequirements(requirementsMany);
		Assert.assertTrue("Test plusieurs listes de prérequis ok", nodeLink.isAvailable(state));
		
		// On fait en sorte qu'il n'ait plus assez d'argent
		moneyRequirement.setAmount(1000);
		
		// [[false, true]]
		nodeLink.setRequirements(requirementsSingle);
		Assert.assertFalse("Test 1 liste de prérequis ko", nodeLink.isAvailable(state));
		
		// [[false], [false, true]]
		nodeLink.setRequirements(requirementsMany);
		Assert.assertFalse("Test plusieurs listes de prérequis ko", nodeLink.isAvailable(state));
	}

}
