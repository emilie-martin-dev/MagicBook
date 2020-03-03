package magic_book.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import magic_book.core.item.BookItem;
import magic_book.core.node.BookNode;


public class Book {
	
	private HashMap<Integer, BookNode> nodes;
	private List<BookItem> items;
	private List<BookCharacter> characters;

	public Book(HashMap<Integer, BookNode> nodes, List<BookItem> items, List<BookCharacter> characters) {
		this.nodes = nodes;
		this.items = items;
		this.characters = characters;
		
		if(this.items == null)
			this.items = new ArrayList<>();
		
		if(this.characters == null)
			this.characters = new ArrayList<>();
	}
	
	public HashMap<Integer, BookNode> getNodes() {
		return nodes;
	}

	public BookNode getRootNode() {
		return nodes.get(1);
	}
	
	public void setNodes(HashMap<Integer, BookNode> nodes) {
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
