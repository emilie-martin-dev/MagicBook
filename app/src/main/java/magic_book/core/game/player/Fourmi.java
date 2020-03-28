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

	public Fourmi(AbstractBookNode bookNodeChoice){
		this.bookNodeChoice = bookNodeChoice;
	}
	
	public BookCharacter execBookState(){
		BookCharacter bookCharacter = new BookCharacter("", "Fourmis", 20, 20, null, null, null, 0, false);
		bookCharacter.changeMoneyAmount("money",100);
		return bookCharacter;
	}
	
	private void execNodeWithRandomChoices(BookNodeWithRandomChoices node, BookState state){
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
		/*
		Question:
		//Je pense mettre cette methode en Bool
		//False si failure
		//True si victory
		*/
		if(node.getBookNodeStatus().VICTORY == BookNodeStatus.VICTORY){	
		}
	}
	
}
