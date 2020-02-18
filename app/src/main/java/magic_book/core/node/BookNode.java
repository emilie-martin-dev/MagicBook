package magic_book.core.node;

import java.util.ArrayList;

public class BookNode {
	
	private String text;
	private BookNodeType nodeType;
	private ArrayList<BookNodeLink> choices;
	
	public BookNode(String text, BookNodeType nodeType, ArrayList<BookNodeLink> choices){
		this.text = text;
		this.nodeType = nodeType;
		this.choices = choices;
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

	public ArrayList<BookNodeLink> getChoices() {
		return choices;
	}

	public void setChoices(ArrayList<BookNodeLink> choices) {
		this.choices = choices;
	}

	@Override
	public String toString(){
		return this.text;
	}

}
