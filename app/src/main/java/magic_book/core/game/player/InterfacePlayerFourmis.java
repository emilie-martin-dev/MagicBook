package magic_book.core.game.player;

import java.util.List;
import magic_book.core.game.BookState;
import magic_book.core.graph.node.AbstractBookNode;
import magic_book.core.graph.node.BookNodeCombat;
import magic_book.core.graph.node.BookNodeTerminal;
import magic_book.core.graph.node.BookNodeWithChoices;
import magic_book.core.graph.node.BookNodeWithRandomChoices;
import magic_book.core.item.BookItem;

public interface InterfacePlayerFourmis {
	
	public int execNodeCombat(int nbr);
	
	public void execNodeTerminal(BookNodeTerminal node);
	
	public void useInventaire();
	
	public BookState verifGetNodeItem(List<String> listItemState, List<BookItem> listItemNode, int nbItemDispo);
	
	
}
