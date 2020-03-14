package magic_book.core.node;

import java.util.ArrayList;
import java.util.List;
import magic_book.core.game.BookState;
import magic_book.core.requirement.AbstractRequirement;

public class BookNodeLink {

	private String text;
	private BookNode destination;
	private List<AbstractRequirement> requirements;
	
	public BookNodeLink(String text, BookNode destination) {
		this(text, destination, null);
	}
	
	public BookNodeLink(String text, BookNode destination, List<AbstractRequirement> requirements) {
		this.text = text;
		this.destination = destination;
		this.requirements = requirements;
		
		if(this.requirements == null)
			this.requirements = new ArrayList<>();
	}
	
	public boolean isAvailable(BookState state) {
		for(AbstractRequirement r : requirements) {
			if(!r.isSatisfied(state))
				return false;
		}
		
		return true;
	}
	
	public void addRequirement(AbstractRequirement requirement) {
		this.requirements.add(requirement);
	}

	public List<AbstractRequirement> getRequirements() {
		return requirements;
	}

	public void setRequirements(List<AbstractRequirement> requirements) {
		this.requirements = requirements;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public BookNode getDestination() {
		return destination;
	}

	public void setDestination(BookNode destination) {
		this.destination = destination;
	}
	
}
