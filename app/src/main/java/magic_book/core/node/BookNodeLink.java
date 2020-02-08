package magic_book.core.node;

public class BookNodeLink {

	private String text;
	private BookNode destination;

	public BookNodeLink(String text, BookNode destination){
		this.text = text;
		this.destination = destination;
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
