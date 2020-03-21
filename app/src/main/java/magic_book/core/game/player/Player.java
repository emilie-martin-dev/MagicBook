package magic_book.core.game.player;

import java.util.Random;
import magic_book.core.graph.node.AbstractBookNode;
import magic_book.core.graph.node.BookNodeCombat;
import magic_book.core.graph.node.BookNodeStatus;
import magic_book.core.graph.node.BookNodeTerminal;
import magic_book.core.graph.node.BookNodeWithChoices;
import magic_book.core.graph.node.BookNodeWithRandomChoices;

public class Player {
	
	private AbstractBookNode bookNodeChoice;
	private int victoire;
	
	public Player(AbstractBookNode bookNodeChoice){
		this.bookNodeChoice = bookNodeChoice;
		doPlay();
	}
	
	private void doPlay(){
		while(!(this.bookNodeChoice instanceof BookNodeTerminal)){
			if (this.bookNodeChoice instanceof BookNodeCombat){
				doNodeCombat();
			} else if (this.bookNodeChoice instanceof BookNodeWithChoices){
				doNodeWithRandomChoices();
			} else if (this.bookNodeChoice instanceof BookNodeWithRandomChoices){
				doNodeWithChoices();
			}
		}
		BookNodeTerminal bookNodeTerminal = (BookNodeTerminal) bookNodeChoice;
		if(bookNodeTerminal.getBookNodeStatus() == BookNodeStatus.VICTORY){
			victoire = 1;
		}
	}
	
	private void doNodeWithChoices(){
		Random random = new Random();
		BookNodeWithChoices bookNodeWithChoices = (BookNodeWithChoices) bookNodeChoice;
		int nbChoices = random.nextInt(bookNodeWithChoices.getChoices().size());
		this.bookNodeChoice = bookNodeWithChoices.getChoices().get(nbChoices).getDestination();
	}
	
	private void doNodeWithRandomChoices(){
		Random random = new Random();
		BookNodeWithRandomChoices bookNodeWithRandomChoices = (BookNodeWithRandomChoices) bookNodeChoice;
		int nbChoices = random.nextInt(bookNodeWithRandomChoices.getChoices().size());
		this.bookNodeChoice = bookNodeWithRandomChoices.getChoices().get(nbChoices).getDestination();
	}
	
	private void doNodeCombat(){
		Random random = new Random();
		BookNodeCombat bookNodeCombat = (BookNodeCombat) bookNodeChoice;
		int nbChoices = random.nextInt(bookNodeCombat.getChoices().size());
		this.bookNodeChoice = bookNodeCombat.getChoices().get(nbChoices).getDestination();
	}
	
	
	public int getVictory(){
		return victoire;
	}
	
}
