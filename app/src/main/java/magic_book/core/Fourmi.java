package magic_book.window;

import java.util.Random;
import java.lang.Math; 
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
	
}
	
	
	
	
	
	