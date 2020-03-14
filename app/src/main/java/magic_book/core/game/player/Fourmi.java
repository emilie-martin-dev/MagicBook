package magic_book.core.game.player;

import java.util.Random;

import magic_book.core.node.AbstractBookNode;
import magic_book.core.node.BookNodeStatus;
import magic_book.core.node.BookNodeTerminal;

public class Fourmi {

	private AbstractBookNode currentNode;
	
	public Fourmi(AbstractBookNode node){
		this.currentNode = node;
	}
	
	public void faireUnChoix() {
		Random rand = new Random();
		
		int nb = rand.nextInt(currentNode.getChoices().size()); 		
		this.currentNode = currentNode.getChoices().get(nb).getDestination(); 
	}

	public AbstractBookNode getCurrentNode() {
		return currentNode;
	}
	
	public static float estimerDifficulteLivre(AbstractBookNode node, int nbFourmi){
		int victory = 0;
		
		for(int i = 0 ; i < nbFourmi ; i++){
			Fourmi f = new Fourmi(node);
			
			while(!(f.getCurrentNode() instanceof BookNodeTerminal)){
				f.faireUnChoix();
			}	
			
			BookNodeTerminal nodeTerminal = (BookNodeTerminal) f.getCurrentNode();
			
			if(nodeTerminal.getBookNodeStatus() == BookNodeStatus.VICTORY){
				victory += 1;
			}
		}	
		
		return ((float)victory / (float)nbFourmi) * 100f;
	}
	
}
