package magic_book.core.node;

public class NodeLink {

	private String text;
	private Node destination;

	public NodeLink(String text, Node destination){
		this.text = text;
		this.destination = destination;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Node getDestination() {
		return destination;
	}

	public void setDestination(Node destination) {
		this.destination = destination;
	}
	
}
