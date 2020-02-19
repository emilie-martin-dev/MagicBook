package magic_book.core;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import magic_book.core.node.BookNode;
import magic_book.core.node.BookNodeLink;
import magic_book.core.node.BookNodeType;
import org.junit.Test;


public class FourmiTest {
	
	public BookNode getBasicStory() {
		BookNode n4 = new BookNode("Vous sentez le pain chaud et les croissants.", BookNodeType.VICTORY, null);
		BookNode n3 = new BookNode("Vous vous êtes perdu...", BookNodeType.FAILURE, null);
		BookNode n2 = new BookNode("Vous appercevez au loin ce qui semble être un magasin", BookNodeType.BASIC, new ArrayList(Arrays.asList(new BookNodeLink("Allez voir", n4))));
		BookNode r = new BookNode("Vous êtes dans un cul de sac", BookNodeType.BASIC, Arrays.asList(new BookNodeLink("Faire demi tour", n2)));
		n2.getChoices().add(new BookNodeLink("Allez à gauche", r));
		BookNode n1 = new BookNode("Vous avez faim, vous cherchez une boulangerie.", BookNodeType.BASIC, Arrays.asList(new BookNodeLink("Tourner à droite", n2), new BookNodeLink("Aller tout droit", n3)));

		return n1;
	}
	
	@Test
	public void estimerDifficulteLivre_test(){	
		BookNode firstNode = getBasicStory();
		firstNode.getChoices().get(1).getDestination().setNodeType(BookNodeType.VICTORY);
		Fourmi fourmi = new Fourmi(firstNode);

		Assert.assertTrue(fourmi.estimerDifficulteLivre(firstNode, 100)== 100.0f);
	}

	@Test
	public void faireUnChoix_test(){
		BookNode firstNode = getBasicStory();
		Fourmi fourmi = new Fourmi(firstNode);

		fourmi.faireUnChoix();
		Assert.assertTrue(fourmi.getCurrentNode() != firstNode);
	}
	
}
