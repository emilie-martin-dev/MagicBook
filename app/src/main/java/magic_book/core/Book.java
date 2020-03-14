package magic_book.core;

import magic_book.core.game.BookCharacter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import magic_book.core.item.BookItem;
import magic_book.core.node.AbstractBookNode;


public class Book {
	
	private HashMap<Integer, AbstractBookNode> nodes;
	private List<BookItem> items;
	private List<BookCharacter> characters;

	public Book(HashMap<Integer, AbstractBookNode> nodes, List<BookItem> items, List<BookCharacter> characters) {
		this.nodes = nodes;
		this.items = items;
		this.characters = characters;
		
		if(this.items == null)
			this.items = new ArrayList<>();
		
		if(this.characters == null)
			this.characters = new ArrayList<>();
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

	public List<BookItem> getItems() {
		return items;
	}

	public void setItems(List<BookItem> items) {
		this.items = items;
	}

	public List<BookCharacter> getCharacters() {
		return characters;
	}

	public void setCharacters(List<BookCharacter> characters) {
		this.characters = characters;
	}

}
