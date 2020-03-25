package magic_book.core.game.player;

import java.util.Random;
import magic_book.core.game.BookCharacter;
import magic_book.core.game.BookState;

import magic_book.core.graph.node.AbstractBookNode;
import magic_book.core.graph.node.BookNodeCombat;
import magic_book.core.graph.node.BookNodeStatus;
import magic_book.core.graph.node.BookNodeTerminal;
import magic_book.core.graph.node.BookNodeWithRandomChoices;
import magic_book.core.graph.node_link.BookNodeLinkRandom;

public class Fourmi implements InterfacePlayerFourmis{

	private AbstractBookNode bookNodeChoice;
	private int victoire;

	public Fourmi(){
		this.bookNodeChoice = bookNodeChoice;
	}
	
	public BookCharacter execBookState(){
		BookCharacter bookCharacter = new BookCharacter("", "Fourmis", 20, 20, null, null, null, 0, false);
		bookCharacter.changeMoneyAmount("money",100);
		return bookCharacter;
	}
	
	public void execNodeWithRandomChoices(BookNodeWithRandomChoices node, BookState state){
		System.out.println("nodeWithChoiceRandom");
		BookNodeWithRandomChoices bookNodeWithRandomChoices = (BookNodeWithRandomChoices) bookNodeChoice;
		BookNodeLinkRandom randomChoices = node.getRandomChoices(state);
		this.bookNodeChoice = randomChoices.getDestination();
	}
	
	@Override
	public void execNodeCombat(BookNodeCombat node, BookState state) {
		Random random = new Random();
		BookNodeCombat bookNodeCombat = (BookNodeCombat) bookNodeChoice;
		int nbChoices = random.nextInt(bookNodeCombat.getChoices().size());
		this.bookNodeChoice = bookNodeCombat.getChoices().get(nbChoices).getDestination();
	}

	@Override
	public void execNodeTerminal(BookNodeTerminal node, BookState state) {
		if(node.getBookNodeStatus() == BookNodeStatus.VICTORY){	
			this.victoire = 1;
		} else {
			this.victoire = 0;
		}
	}

	public AbstractBookNode getBookNodeChoice() {
		return bookNodeChoice;
	}

	public int getVictoire() {
		return victoire;
	}
	
	
	
}
