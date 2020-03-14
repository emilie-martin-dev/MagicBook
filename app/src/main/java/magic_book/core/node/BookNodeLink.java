package magic_book.core.node;

public class BookNodeLink {

	private String text;
	private AbstractBookNode destination;

	public BookNodeLink(String text, AbstractBookNode destination){
		this.text = text;
		this.destination = destination;
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
	
}
