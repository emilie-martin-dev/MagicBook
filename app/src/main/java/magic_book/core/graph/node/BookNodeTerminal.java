package magic_book.core.graph.node;

import java.util.ArrayList;
import magic_book.core.graph.node_link.BookNodeLink;

public class BookNodeTerminal extends AbstractBookNode {

	private BookNodeStatus bookNodeStatus;

	public BookNodeTerminal(String texte, BookNodeStatus bookNodeStatus){
		super(texte);
		
		this.bookNodeStatus = bookNodeStatus;
	}
	public BookNodeStatus getBookNodeStatus() {
		return bookNodeStatus;
	}

	public void setBookNodeStatus(BookNodeStatus bookNodeStatus) {
		this.bookNodeStatus = bookNodeStatus;
	}

	@Override
	public ArrayList<BookNodeLink> getChoices() {
		return new ArrayList<>();
	}

}