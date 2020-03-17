package magic_book.core;

import magic_book.core.game.BookCharacter;
import java.util.HashMap;
import magic_book.core.item.BookItem;
import magic_book.core.node.AbstractBookNode;


public class Book {
	
	private String textPrelude;
	private HashMap<Integer, AbstractBookNode> nodes;
	private HashMap<String, BookItem> items;
	private HashMap<String, BookCharacter> characters;

	public Book(String textPrelude, HashMap<Integer, AbstractBookNode> nodes, HashMap<String, BookItem> items, HashMap<String, BookCharacter> characters) {
 		this.textPrelude = textPrelude;
		this.nodes = nodes;
		this.items = items;
		this.characters = characters;
		
		if(this.items == null)
			this.items = new HashMap<>();
		
		if(this.characters == null)
			this.characters = new HashMap<>();
	}

	public String getTextPrelude() {
		return textPrelude;
	}

	public void setTextPrelude(String textPrelude) {
		this.textPrelude = textPrelude;
	}
	
	public HashMap<Integer, AbstractBookNode> getNodes() {
		return nodes;
	}

	public AbstractBookNode getRootNode() {
		return nodes.get(1);
	}
	
	public void setNodes(HashMap<Integer, AbstractBookNode> nodes) {
		this.nodes = nodes;
	}

	public HashMap<String, BookItem> getItems() {
		return items;
	}

	public void setItems(HashMap<String, BookItem> items) {
		this.items = items;
	}

	public HashMap<String, BookCharacter> getCharacters() {
		return characters;
	}

	public void setCharacters(HashMap<String, BookCharacter> characters) {
		this.characters = characters;
	}

}
