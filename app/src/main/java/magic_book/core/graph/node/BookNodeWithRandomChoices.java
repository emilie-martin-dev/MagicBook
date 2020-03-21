package magic_book.core.graph.node;

import java.util.ArrayList;
import java.util.List;

import magic_book.core.item.BookItemLink;
import magic_book.core.graph.node_link.BookNodeLinkRandom;

import java.util.Random;
import magic_book.core.game.BookState;

public class BookNodeWithRandomChoices extends AbstractBookNodeWithChoices<BookNodeLinkRandom>{
	
	public BookNodeWithRandomChoices(String text) {
		this(text, 0, null, null, null);
	}
	
	public BookNodeWithRandomChoices(String text, Integer nbItemsAPrendre, List<BookItemLink> itemLinks, List<BookItemLink> shopItemLinks, List<BookNodeLinkRandom> choices) {
		super(text, nbItemsAPrendre, itemLinks, shopItemLinks, choices);
	}
	
	public BookNodeLinkRandom getRandomChoices(BookState state){
		List<BookNodeLinkRandom> listNodeLinkDisponible = new ArrayList();
		int somme = 0;
		int nbrChoice = 0;
		for (int i = 0 ; i < this.choices.size() ; i++){
			if(this.choices.get(i).isAvailable(state)){
				listNodeLinkDisponible.add(this.choices.get(i));
				somme += this.choices.get(i).getChance();
			}
		}
		if(listNodeLinkDisponible.isEmpty()){
			BookNodeTerminal bookNodeTerminalFail = new BookNodeTerminal("Dommage.. Vous êtes mort", BookNodeStatus.FAILURE);
			BookNodeLinkRandom bookNodeLinkTerminal = new BookNodeLinkRandom("C'est la voie de la raison", bookNodeTerminalFail, null, 0);
			return bookNodeLinkTerminal;
		} else {
			Random random = new Random();
			int nbrRandomChoice = random.nextInt(somme);
			for (int i = 0 ; i < listNodeLinkDisponible.size() ; i++){
				nbrRandomChoice -= this.choices.get(i).getChance();
				if(nbrRandomChoice <= 0)
					nbrChoice = i;
					break;
			}
		}
		return this.choices.get(nbrChoice) ;
	}
	
	
}
