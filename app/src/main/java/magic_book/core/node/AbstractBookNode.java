package magic_book.core.node;

public abstract class AbstractBookNode {
	
	private String text;
	
	public AbstractBookNode(String text){
		this.text = text;
	}
	public abstract boolean isTerminal();

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	
}
