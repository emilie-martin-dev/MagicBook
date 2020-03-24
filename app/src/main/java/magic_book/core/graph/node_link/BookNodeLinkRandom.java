package magic_book.core.graph.node_link;

import magic_book.core.graph.node.AbstractBookNode;
import java.util.List;
import magic_book.core.Book;
import magic_book.core.file.json.ChoiceJson;
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
	
	@Override
	public String getDescription(Book book) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(super.getDescription(book));
		buffer.append("Ce noeud a ");
		buffer.append(chance);
		buffer.append(" chances d'être sélectionné.\n");
		
		return buffer.toString();
	}

	@Override
	public ChoiceJson toJson() {
		ChoiceJson choiceJson = super.toJson();
		
		choiceJson.setWeight(chance);
		
		return choiceJson;
	}

	@Override
	public void fromJson(ChoiceJson json) {
		super.fromJson(json);
		
		chance = json.getWeight();
	}
	
	public int getChance() {
		return chance;
	}

	public void setChance(int chance) {
		this.chance = chance;
	}
	
}
