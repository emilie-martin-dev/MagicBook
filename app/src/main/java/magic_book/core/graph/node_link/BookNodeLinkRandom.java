package magic_book.core.graph.node_link;

import java.util.HashMap;
import magic_book.core.graph.node.AbstractBookNode;
import java.util.List;
import magic_book.core.Book;
import magic_book.core.requirement.AbstractRequirement;

public class BookNodeLinkRandom extends BookNodeLink {
	
	private int chance;
	
	public BookNodeLinkRandom() {
		this("", null, 1);
	}
	
	public BookNodeLinkRandom(String text, AbstractBookNode destination, int chance) {
		this(text, destination, null, chance);
	}
	
	public BookNodeLinkRandom(String text, AbstractBookNode destination, List<List<AbstractRequirement>> requirements, int chance) {
		super(text, destination, requirements);
		
		this.chance = chance;
	}
	
	public String getTextForBookText(Book book, HashMap<AbstractBookNode, Integer> nodesIndex) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(super.getTextForBookText(book, nodesIndex));
		buffer.append("Ce noeud a ");
		buffer.append(chance);
		buffer.append(" chances d'être sélectionné.\n");
		
		return buffer.toString();
	}

	public int getChance() {
		return chance;
	}

	public void setChance(int chance) {
		this.chance = chance;
	}
	
}
