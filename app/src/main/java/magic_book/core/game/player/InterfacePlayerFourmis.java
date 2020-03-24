package magic_book.core.game.player;

import magic_book.core.game.BookState;
import magic_book.core.graph.node.AbstractBookNode;
import magic_book.core.graph.node.BookNodeCombat;
import magic_book.core.graph.node.BookNodeTerminal;
import magic_book.core.graph.node.BookNodeWithChoices;

public interface InterfacePlayerFourmis {
	
	public void execNodeCombat(BookNodeCombat node, BookState state);
	
	public void execNodeTerminal(BookNodeTerminal node, BookState state);	
}
