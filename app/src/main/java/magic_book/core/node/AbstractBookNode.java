package magic_book.core.node;

import java.util.List;

public abstract class AbstractBookNode {
	
	private String text;
	
	public AbstractBookNode(String text){
		this.text = text;
	}
	
	public abstract <T extends BookNodeLink> List<T> getChoices();

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	
}
