package magic_book.core.graph.node;

import java.util.List;
import magic_book.core.graph.node_link.BookNodeLink;

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
