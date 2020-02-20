package magic_book.core.node;

import java.util.ArrayList;
import java.util.List;

public class BookNode {
	
	private String text;
	private BookNodeType nodeType;
	private List<BookNodeLink> choices;
	
	public BookNode(String text, BookNodeType nodeType, List<BookNodeLink> choices){
		this.text = text;
		this.nodeType = nodeType;
		this.choices = choices;
		
		if(this.choices == null) 
			this.choices = new ArrayList<>();
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public BookNodeType getNodeType() {
		return nodeType;
	}

	public void setNodeType(BookNodeType nodeType) {
		this.nodeType = nodeType;
	}

	public List<BookNodeLink> getChoices() {
		return choices;
	}

	public void setChoices(List<BookNodeLink> choices) {
		this.choices = choices;
	}

	@Override
	public String toString(){
		return this.text;
	}

}
