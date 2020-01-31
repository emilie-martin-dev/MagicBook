package magic_book.core.node;

import java.util.ArrayList;

public class Node {
	
	private String text;
	private NodeType nodeType;
	private ArrayList<NodeLink> choices;
	
	public Node(String text, NodeType nodeType, ArrayList<NodeLink> choices){
		this.text = text;
		this.nodeType = nodeType;
		this.choices = choices;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public NodeType getNodeType() {
		return nodeType;
	}

	public void setNodeType(NodeType nodeType) {
		this.nodeType = nodeType;
	}

	public ArrayList<NodeLink> getChoices() {
		return choices;
	}

	public void setChoices(ArrayList<NodeLink> choices) {
		this.choices = choices;
	}
	
	@Override
	public String toString(){
		return this.text;
	}
	
}
