package magic_book.core.graph.node;

import java.util.HashMap;
import java.util.List;
import magic_book.core.Book;
import magic_book.core.graph.node_link.BookNodeLink;
import magic_book.core.parser.TextParser;

public abstract class AbstractBookNode {
	
	private String text;
	
	public AbstractBookNode(String text){
		this.text = text;
	}
	
	public abstract <T extends BookNodeLink> List<T> getChoices();

	public String getTextForBookText(Book book, HashMap<AbstractBookNode, Integer> nodesIndex) {
		return TextParser.parseText(text, book.getItems(), book.getCharacters())+"\n";
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
