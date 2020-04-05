package magic_book.core.utils;

import magic_book.test.AbstractTest;
import org.junit.Test;

public class FourmiTest extends AbstractTest {
	
	@Test
	public void estimerDifficulteLivre_test(){	
		/*BookNodeTerminal end = new BookNodeTerminal("Vous sentez le pain chaud et les croissants.", BookNodeStatus.VICTORY);
		BookNodeWithChoices root = new BookNodeWithChoices("Vous avez faim, vous cherchez une boulangerie.");
		root.addChoices(new BookNodeLink("Aller tout droit", end));
		
		Assert.assertTrue(Fourmi.estimerDifficulteLivre(root, 100) == 100.0f);
		
		end.setBookNodeStatus(BookNodeStatus.FAILURE);
		Assert.assertTrue(Fourmi.estimerDifficulteLivre(root, 100) == 0.0f);*/
	}

	@Test
	public void faireUnChoix_test(){
		/*BookNodeTerminal end = new BookNodeTerminal("Vous sentez le pain chaud et les croissants.", BookNodeStatus.VICTORY);
		BookNodeWithChoices root = new BookNodeWithChoices("Vous avez faim, vous cherchez une boulangerie.");
		root.addChoices(new BookNodeLink("Aller tout droit", end));
		
		Fourmi fourmi = new Fourmi(root);

		fourmi.faireUnChoix();
		Assert.assertTrue(fourmi.getCurrentNode() != root);*/
	}
	
}
