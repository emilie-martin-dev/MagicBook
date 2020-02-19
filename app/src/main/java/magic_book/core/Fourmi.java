package magic_book.core;

import java.util.Random;

import magic_book.core.node.BookNode;
import magic_book.core.node.BookNodeType;

public class Fourmi {

	private BookNode currentNode;
	
	public Fourmi(BookNode node){
		this.currentNode = node;
	}
	
	public void faireUnChoix() {
		Random rand = new Random();
		
		int nb = rand.nextInt(currentNode.getChoices().size()); // en fonction du nombre de choix possible prends une direction aléatoire
		this.currentNode = currentNode.getChoices().get(nb).getDestination(); // change de noeud
	}

	public BookNode getCurrentNode() {
		return currentNode;
	}
	
	public static float estimerDifficulteLivre(BookNode node, int nbFourmi){
		int victory = 0;
		
		for(int i = 0 ; i < nbFourmi ; i++){ // creer les fourmis
			Fourmi f = new Fourmi(node);
			
			while(f.getCurrentNode().getNodeType() == BookNodeType.BASIC){
				f.faireUnChoix();
			}	
			
			if(f.getCurrentNode().getNodeType() == BookNodeType.VICTORY){
				victory += 1;
			}
		}	
		
		return ((float)victory / (float)nbFourmi) * 100f;
	}
	
}
	
	
	
	
	
	