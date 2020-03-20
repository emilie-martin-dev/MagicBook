package magic_book.core.item;

import java.util.HashMap;
import magic_book.core.Book;
import magic_book.core.graph.node.AbstractBookNode;
import magic_book.core.parser.Parsable;

public class BookItem implements Parsable {

	private String id;
	private String name;

	public BookItem(String id, String nom) {
		this.id = id;
		this.name = nom;
	}
	
	public String getTextForBookText(Book book, HashMap<AbstractBookNode, Integer> nodesInv) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("Nom : ");
		buffer.append(name);
		buffer.append("\n");
		
		return buffer.toString();
	}

	@Override
	public String getParsableId() {
		return this.id;
	}

	@Override
	public String getParsableText() {
		return this.name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@Override
	public String toString() {
		return this.name;
	}
}