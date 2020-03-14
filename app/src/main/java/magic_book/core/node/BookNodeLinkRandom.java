package magic_book.core.node;

import java.util.List;
import magic_book.core.requirement.AbstractRequirement;

public class BookNodeLinkRandom extends BookNodeLink {
	
	private int chance;
	
	public BookNodeLinkRandom(String text, AbstractBookNode destination, int chance) {
		this(text, destination, null, chance);
	}
	
	public BookNodeLinkRandom(String text, AbstractBookNode destination, List<AbstractRequirement> requirements, int chance) {
		super(text, destination, requirements);
		
		this.chance = chance;
	}

	public int getChance() {
		return chance;
	}

	public void setChance(int chance) {
		this.chance = chance;
	}
	
}
