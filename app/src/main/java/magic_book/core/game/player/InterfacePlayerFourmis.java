package magic_book.core.game.player;

import magic_book.core.game.BookState;
import magic_book.core.graph.node.AbstractBookNode;
import magic_book.core.graph.node.BookNodeCombat;
import magic_book.core.graph.node.BookNodeTerminal;
import magic_book.core.graph.node.BookNodeWithChoices;
import magic_book.core.graph.node.BookNodeWithRandomChoices;

public interface InterfacePlayerFourmis {
	
	public void execNodeCombat(BookNodeCombat node);
	
	public void execNodeWithChoices(BookNodeWithChoices node);
	
	public void execNodeWithRandomChoices(BookNodeWithRandomChoices node);
	
	public void execNodeTerminal(BookNodeTerminal node);	
}
