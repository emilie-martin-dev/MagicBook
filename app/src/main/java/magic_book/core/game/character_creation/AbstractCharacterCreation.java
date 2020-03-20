package magic_book.core.game.character_creation;

import java.util.HashMap;
import magic_book.core.Book;
import magic_book.core.graph.node.AbstractBookNode;


public abstract class AbstractCharacterCreation {

	private String text;

	public AbstractCharacterCreation(String text) {
		this.text = text;
	}
	
	public String getTextForBookText(Book book, HashMap<AbstractBookNode, Integer> nodesInv) {
		return this.text + "\n";
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
