package magic_book.core.graph.node_link;

import magic_book.core.graph.node.AbstractBookNode;
import java.util.ArrayList;
import java.util.List;
import magic_book.core.game.BookState;
import magic_book.core.requirement.AbstractRequirement;

public class BookNodeLink {

	private String text;
	private AbstractBookNode destination;
	private List<List<AbstractRequirement>> requirements;
	private Integer hp;
	private Integer gold;
	private Boolean auto;
	
	public BookNodeLink() {
		this("", null);
	}
	
	public BookNodeLink(String text, AbstractBookNode destination) {
		this(text, destination, null);
	}
	
	public BookNodeLink(String text, AbstractBookNode destination, List<List<AbstractRequirement>> requirements) {
		this(text, destination, requirements, null, null, null);
	}
	
	public BookNodeLink(String text, AbstractBookNode destination, List<List<AbstractRequirement>> requirements, Integer hp, Integer gold, Boolean auto) {
		this.text = text;
		this.destination = destination;
		this.requirements = requirements;
		this.hp = hp;
		this.gold = gold;
		this.auto = auto;
		
		if(this.requirements == null)
			this.requirements = new ArrayList<>();
	}
	
	public boolean isAvailable(BookState state) {
		for(List<AbstractRequirement> groupRequirement : requirements) {
			boolean satisfied = true;
			for(AbstractRequirement r : groupRequirement) {
				if(!r.isSatisfied(state)) {
					satisfied = false;
					break;
				}
			}
			
			if(satisfied)
				return true;
		}
		
		return false;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public AbstractBookNode getDestination() {
		return destination;
	}

	public void setDestination(AbstractBookNode destination) {
		this.destination = destination;
	}

	public List<List<AbstractRequirement>> getRequirements() {
		return requirements;
	}

	public void setRequirements(List<List<AbstractRequirement>> requirements) {
		this.requirements = requirements;
	}

	public Integer getHp() {
		return hp;
	}

	public void setHp(Integer hp) {
		this.hp = hp;
	}

	public Integer getGold() {
		return gold;
	}

	public void setGold(Integer gold) {
		this.gold = gold;
	}

	public Boolean getAuto() {
		return auto;
	}

	public void setAuto(Boolean auto) {
		this.auto = auto;
	}
	
		
}
